package com.example.construction.util;

public enum OfferStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED");

    private final String name;

    OfferStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
