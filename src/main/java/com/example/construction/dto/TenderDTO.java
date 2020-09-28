package com.example.construction.dto;

import com.example.construction.util.TenderStatus;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

public class TenderDTO extends RepresentationModel<TenderDTO> {
    private Long id;
    private String name;
    private IssuerDTO issuer;
    private TenderStatus tenderStatus;
    private Set<OfferDTO> offers;

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

    public IssuerDTO getIssuer() {
        return issuer;
    }

    public void setIssuer(IssuerDTO issuer) {
        this.issuer = issuer;
    }

    public TenderStatus getTenderStatus() {
        return tenderStatus;
    }

    public void setTenderStatus(TenderStatus tenderStatus) {
        this.tenderStatus = tenderStatus;
    }

    public Set<OfferDTO> getOffers() {
        return offers;
    }

    public void setOffers(Set<OfferDTO> offers) {
        this.offers = offers;
    }
}
