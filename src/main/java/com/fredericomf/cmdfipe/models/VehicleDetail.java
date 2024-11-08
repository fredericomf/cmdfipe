package com.fredericomf.cmdfipe.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VehicleDetail(
                @JsonAlias("TipoVeiculo") Integer type,
                @JsonAlias("Valor") String value,
                @JsonAlias("Marca") String brand,
                @JsonAlias("Modelo") String model,
                @JsonAlias("AnoModelo") Integer modelYear,
                @JsonAlias("Combustivel") String fuel,
                @JsonAlias("CodigoFipe") String fipeCode,
                @JsonAlias("MesReferencia") String referenceMonth,
                @JsonAlias("SiglaCombustivel") String fuelCode) {
}
