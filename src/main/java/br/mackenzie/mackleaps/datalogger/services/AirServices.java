package br.mackenzie.mackleaps.datalogger.services;

import br.mackenzie.mackleaps.datalogger.dtos.AirReportDTO;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
import com.influxdb.query.FluxRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AirServices {

    private static final Logger logger = LoggerFactory.getLogger(AirServices.class);
    private static final String bucket = "airquality";
    private final InfluxDBClient influxDBClient;

    @Value("${influxdb.org}")
    private String orgId;

    public AirServices(InfluxDBClient influxDBClient) {
        this.influxDBClient = influxDBClient;
    }

    public void createAirPoint(AirReportDTO report, String bucket) {
        if (bucket == null || bucket.isEmpty()) {
            throw new IllegalStateException("Bucket name is not configured.");
        }

        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        Point point = Point.measurement("air_quality_report")
                .addField("temperature", report.getTemperature())
                .addField("humidity", report.getHumidity())
                .addField("smoke", report.getSmoke())
                .addField("carbon_dioxide", report.getCarbon_dioxide())
                .addField("carbon_monoxide", report.getCarbon_monoxide())
                .addField("flammable_gases", report.getFlammable_gases())
                .time(System.currentTimeMillis(), WritePrecision.MS);

        writeApi.writePoint(bucket, orgId, point);
        logger.info("Air quality data written to InfluxDB bucket: {}", bucket);
    }

    public List<FluxRecord> queryData(String startDate, String endDate) {

        String start = LocalDate.parse(startDate).atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
        String end = LocalDate.parse(endDate).atTime(23, 59, 59).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);

        logger.info("Query parameters: start={}, end={}", start, end);

        String fluxQuery = String.format(
                "from(bucket: \"%s\") |> range(start: %s, stop: %s) |> filter(fn: (r) => r[\"_measurement\"] == \"air_quality_report\")",
                bucket, start, end
        );

        logger.info("Executing query: {}", fluxQuery);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(fluxQuery);
        List<FluxRecord> records = new ArrayList<>();

        for (FluxTable table : tables) {
            records.addAll(table.getRecords());
        }

        return records;
    }
}