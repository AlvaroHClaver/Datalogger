package br.mackenzie.mackleaps.datalogger.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeatherReportDTO {
    private String station;
    private String timestamp;
    private double windSpeed;
    private double windDirection;
    private double temperature;
    private double humidityRel;
    private double airPressure;
    private double radiation;
    private double precipitation;
    private double leafMoistening;
    private double tensiometer;
}