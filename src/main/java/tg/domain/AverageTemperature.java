package tg.domain;

import tg.webui.AverageTemperatureDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record AverageTemperature(String year, double averageTemperature) {

    public AverageTemperatureDTO toDTO() {
        BigDecimal avgTemp = BigDecimal.valueOf(averageTemperature)
                                       .setScale(1, RoundingMode.HALF_DOWN);
        return new AverageTemperatureDTO(year, avgTemp);
    }
}
