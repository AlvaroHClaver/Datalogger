//package br.mackenzie.mackleaps.datalogger.services;
//
//import br.mackenzie.mackleaps.datalogger.dtos.UtilitiesReportDTO;
//import com.influxdb.client.InfluxDBClient;
//import com.influxdb.client.QueryApi;
//import com.influxdb.client.WriteApiBlocking;
//import com.influxdb.client.domain.WritePrecision;
//import com.influxdb.client.write.Point;
//import com.influxdb.query.FluxTable;
//import com.influxdb.query.FluxRecord;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.ZoneOffset;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class UtilitiesServices {
//
//    private static final Logger logger = LoggerFactory.getLogger(UtilitiesServices.class);
//    private static final String bucket = "utilities";
//    private final InfluxDBClient influxDBClient;
//
//    @Value("${influxdb.org}")
//    private String orgId;
//
//    public UtilitiesServices(InfluxDBClient influxDBClient) {
//        this.influxDBClient = influxDBClient;
//    }
//
//    public void createUtilitiesPoint(UtilitiesReportDTO report, String bucket) {
//        if (bucket == null || bucket.isEmpty()) {
//            throw new IllegalStateException("Bucket name is not configured.");
//        }
//
//        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
//
//        Point point = Point.measurement("utilities_report")
//                .addTag("station", report.getStation())
//                .addField("electricityConsumption", report.getElectricityConsumption())
//                .addField("gasConsumption", report.getGasConsumption())
//                .addField("waterConsumption", report.getWaterConsumption())
//                .addField("voltage", report.getVoltage())
//                .addField("current", report.getCurrent())
//                .addField("powerFactor", report.getPowerFactor())
//                .addField("energyCost", report.getEnergyCost())
//                .addField("operatingTime", report.getOperatingTime())
//                .addField("efficiency", report.getEfficiency())
//                .time(Instant.now(), WritePrecision.NS);
//
//        writeApi.writePoint(bucket, orgId, point);
//        logger.info("Utilities data written to InfluxDB bucket: {}", bucket);
//    }
//
//    public List<FluxRecord> queryData(String startDate, String endDate, String stationName) {
//
//        String start = LocalDate.parse(startDate).atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
//        String end = LocalDate.parse(endDate).atTime(23, 59, 59).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
//
//        String defaultStation = "CRAAM";
//        String station = (stationName != null && (stationName.equals("CRAAM") || stationName.equals("FAU"))) ? stationName : defaultStation;
//
//        logger.info("Query parameters: start={}, end={}, station={}", start, end, station);
//
//        String fluxQuery = String.format(
//                "from(bucket: \"%s\") |> range(start: %s, stop: %s) |> filter(fn: (r) => r[\"_measurement\"] == \"utilities_report\" and r[\"station\"] == \"%s\")",
//                bucket, start, end, station
//        );
//
//        logger.info("Executing query: {}", fluxQuery);
//
//        QueryApi queryApi = influxDBClient.getQueryApi();
//        List<FluxTable> tables = queryApi.query(fluxQuery);
//        List<FluxRecord> records = new ArrayList<>();
//
//        for (FluxTable table : tables) {
//            records.addAll(table.getRecords());
//        }
//
//        return records;
//    }
//}
