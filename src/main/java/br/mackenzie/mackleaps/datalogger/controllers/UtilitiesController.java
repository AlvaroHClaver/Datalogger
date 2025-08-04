//package br.mackenzie.mackleaps.datalogger.controllers;
//
//import br.mackenzie.mackleaps.datalogger.services.UtilitiesServices;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import br.mackenzie.mackleaps.datalogger.dtos.UtilitiesReportDTO;
//import com.influxdb.query.FluxRecord;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/utilities")
//public class UtilitiesController {
//
//    public UtilitiesController(UtilitiesServices influxServices) {
//        this.influxServices = influxServices;
//    }
//
//    private final UtilitiesServices influxServices;
//    private static final Logger logger = LoggerFactory.getLogger(UtilitiesController.class);
//
//    @PostMapping(consumes = "application/json", produces = "application/json", path = "/{bucket}")
//    public ResponseEntity<String> postData(@RequestBody UtilitiesReportDTO report, @PathVariable String bucket) {
//        try {
//            influxServices.createUtilitiesPoint(report, bucket);
//            logger.info("Utilities data successfully recorded!");
//            return new ResponseEntity<>("Data successfully recorded!", HttpStatus.CREATED);
//        } catch (Exception e) {
//            logger.error("Failed to insert utilities data into the database: {}", e.getMessage());
//            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping(produces = "application/json", path = "/{bucket}")
//    public ResponseEntity<List<FluxRecord>> getData(@PathVariable String bucket) {
//        try {
//            String fluxQuery = "from(bucket: \"" + bucket + "\") |> range(start: -30d)";
//            List<FluxRecord> records = influxServices.queryData(fluxQuery);
//            return ResponseEntity.ok(records);
//        } catch (Exception e) {
//            logger.error("Failed to retrieve utilities data: {}", e.getMessage());
//            return ResponseEntity.status(500).body(null);
//        }
//    }
//}
