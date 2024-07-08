package tg.domain;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class LargeFileSparkComputer {

    private final String filePath;

    LargeFileSparkComputer(@Value("${file_path}") String filePath) {
        this.filePath = filePath;
    }

    private final static String YEAR_COL = "year";
    private final static String CITY_COL = "city";
    private final static String DATE_COL = "date";
    private final static String TEMPERATURE_COL = "temperature";
    private final static String AVG_TEMPERATURE_COL = "average_temperature";

    List<AverageTemperature> computeAverageTemperature(final String city) {
        SparkSession spark = SparkSession.builder()
                .appName("Average temperature computer")
                .master("local[*]")
                .getOrCreate();

        Dataset<Row> dataset = spark.read()
                .format("csv")
                .option("header", false)
                .option("sep", ";")
                .load(filePath)
                .toDF(CITY_COL, DATE_COL, TEMPERATURE_COL);

        List<Row> results = dataset.withColumn(YEAR_COL, functions.year(dataset.col(DATE_COL)))
                .filter(functions.lower(functions.col(CITY_COL)).equalTo(city))
                .groupBy(YEAR_COL, CITY_COL)
                .agg(functions.avg(TEMPERATURE_COL).alias(AVG_TEMPERATURE_COL))
                .sort(functions.col(YEAR_COL).asc())
                .collectAsList();

        spark.stop();
        return results.stream()
                .map(row -> new AverageTemperature(row.getAs(YEAR_COL).toString(), row.getAs(AVG_TEMPERATURE_COL)))
                .toList();
    }

}
