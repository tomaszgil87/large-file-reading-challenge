package tg.webui;

import org.springframework.web.bind.annotation.*;
import tg.domain.AverageTemperature;
import tg.domain.LargeFileReadingService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("temperature/average")
public class LargeFileReadingController {

    private final LargeFileReadingService largeFileReadingService;

    public LargeFileReadingController(final LargeFileReadingService largeFileReadingService) {
        this.largeFileReadingService = largeFileReadingService;
    }

    @GetMapping(value = "/{city}")
    @ResponseStatus(OK)
    public List<AverageTemperatureDTO> getAverageTemperature (@PathVariable("city") String city) {
        return largeFileReadingService.getAverageTemperature(city)
                                      .stream()
                                      .map(AverageTemperature::toDTO)
                                      .toList();
    }
}
