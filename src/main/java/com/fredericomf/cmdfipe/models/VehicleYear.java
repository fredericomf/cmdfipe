package com.fredericomf.cmdfipe.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public record VehicleYear(@JsonAlias("codigo") String id, @JsonAlias("nome") String name) {
}
