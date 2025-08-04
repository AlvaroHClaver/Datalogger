package br.mackenzie.mackleaps.datalogger.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.mackenzie.mackleaps.datalogger.dtos.WeatherReportDTO;
import br.mackenzie.mackleaps.datalogger.services.WeatherServices;
import com.influxdb.query.FluxRecord;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private final WeatherServices influxServices;

    public WeatherController(WeatherServices influxServices) {
        this.influxServices = influxServices;
    }

    @PostMapping(consumes = "application/json", produces = "application/json", path= "/weather")
    public ResponseEntity<String> postData(@RequestBody WeatherReportDTO report, @PathVariable String bucket) {
        try {
            influxServices.createWeatherPoint(report, bucket);
            logger.info("Weather data successfully recorded!");
            return ResponseEntity.status(HttpStatus.CREATED).body("Data successfully recorded!");
        } catch (Exception e) {
            logger.error("Failed to insert weather data into the database: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping(produces = "application/json", path = "/weather")
    public ResponseEntity<List<FluxRecord>> getData(
            @RequestParam (required = false) String start,
            @RequestParam (required = false) String end,
            @RequestParam (required = false) String station) {
        try {
            List<FluxRecord> records = influxServices.queryData(start, end, station);
            if (records.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(records);
            }
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            logger.error("Failed to retrieve weather data: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
