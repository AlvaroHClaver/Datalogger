package br.mackenzie.mackleaps.datalogger.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.mackenzie.mackleaps.datalogger.dtos.AirReportDTO;
import br.mackenzie.mackleaps.datalogger.services.AirServices;
import com.influxdb.query.FluxRecord;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
public class AirController {

    private static final Logger logger = LoggerFactory.getLogger(AirController.class);
    private final AirServices influxServices;

    public AirController(AirServices influxServices) {
        this.influxServices = influxServices;
    }

    @PostMapping(consumes = "application/json", produces = "application/json", path= "/airquality")
    public ResponseEntity<String> postData(@RequestBody AirReportDTO report, @PathVariable String bucket) {
        try {
            influxServices.createAirPoint(report, bucket);
            logger.info("Air quality data successfully recorded!");
            return ResponseEntity.status(HttpStatus.CREATED).body("Data successfully recorded!");
        } catch (Exception e) {
            logger.error("Failed to insert air quality data into the database: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping(produces = "application/json", path = "/airquality")
    public ResponseEntity<List<FluxRecord>> getData(
            @RequestParam (required = false) String start,
            @RequestParam (required = false) String end) {
        try {
            List<FluxRecord> records = influxServices.queryData(start, end);
            if (records.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(records);
            }
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            logger.error("Failed to retrieve air quality data: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
