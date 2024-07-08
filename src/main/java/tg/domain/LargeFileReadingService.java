package tg.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LargeFileReadingService {

    private final LargeFileSparkComputer largeFileSparkComputer;

    public LargeFileReadingService(final LargeFileSparkComputer largeFileSparkComputer) {
        this.largeFileSparkComputer = largeFileSparkComputer;
    }

    public List<AverageTemperature> getAverageTemperature(final String city) {
        return largeFileSparkComputer.computeAverageTemperature(city);
    }

}
