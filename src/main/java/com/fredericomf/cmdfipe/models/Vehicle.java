package com.fredericomf.cmdfipe.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Vehicle(@JsonAlias("codigo") Integer id, @JsonAlias("nome") String name) {
}
