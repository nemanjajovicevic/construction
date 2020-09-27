package com.example.construction.dto;

import com.example.construction.model.Issuer;
import org.springframework.hateoas.RepresentationModel;

public class TenderDTO extends RepresentationModel<TenderDTO> {
    private Long id;
    private String name;
    private Issuer issuer;

    public TenderDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }
}
