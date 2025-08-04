package br.mackenzie.mackleaps.datalogger.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AirReportDTO {
    private String timestamp;
    private double temperature;
    private double humidity;
    private double smoke;
    private double carbon_dioxide;
    private double carbon_monoxide;
    private double flammable_gases;
}
