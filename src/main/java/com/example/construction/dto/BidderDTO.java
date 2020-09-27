package com.example.construction.dto;

public class BidderDTO {
    private Long id;
    private String name;

    public BidderDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
