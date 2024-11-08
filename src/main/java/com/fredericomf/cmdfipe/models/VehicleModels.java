package com.fredericomf.cmdfipe.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VehicleModels(@JsonAlias("modelos") List<Vehicle> models) {
}
