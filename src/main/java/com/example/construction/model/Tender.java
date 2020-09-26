package com.example.construction.model;

import com.example.construction.util.TenderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Tender extends RepresentationModel<Tender> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @JsonIgnore
    @ManyToOne
    private Issuer issuer;
    private TenderStatus tenderStatus;
    @OneToMany(targetEntity=Offer.class)
    private Set<Offer> offers;

    public Tender() {
    }

    public Tender(Long id, String name, Issuer issuer, TenderStatus tenderStatus, Set<Offer> offers) {
        this.id = id;
        this.name = name;
        this.issuer = issuer;
        this.tenderStatus = tenderStatus;
        this.offers = offers;
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

    public TenderStatus getTenderStatus() {
        return tenderStatus;
    }

    public void setTenderStatus(TenderStatus tenderStatus) {
        this.tenderStatus = tenderStatus;
    }

    public Set getOffers() {
        return offers;
    }

    public void setOffers(Set offers) {
        this.offers = offers;
    }
}
