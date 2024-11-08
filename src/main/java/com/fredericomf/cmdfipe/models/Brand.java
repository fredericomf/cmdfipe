package com.fredericomf.cmdfipe.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Brand(@JsonAlias("codigo") String id, @JsonAlias("nome") String name) {
}
