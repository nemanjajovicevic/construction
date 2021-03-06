package com.example.construction.util;

public enum TenderStatus {
    OPENED("OPENED"),
    CLOSED("CLOSED");

    private final String name;

    TenderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
