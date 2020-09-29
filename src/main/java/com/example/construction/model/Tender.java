package com.example.construction.model;

import com.example.construction.util.TenderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Tender {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    @JsonIgnore
    private Issuer issuer;
    private TenderStatus tenderStatus;
    @OneToMany(targetEntity=Offer.class)
    @JsonIgnore
    private Set<Offer> offers = new HashSet<>();

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

    public Set<Offer> getOffers() {
        return offers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tender tender = (Tender) o;
        return Objects.equals(id, tender.id) &&
                Objects.equals(name, tender.name) &&
                Objects.equals(issuer, tender.issuer) &&
                tenderStatus == tender.tenderStatus &&
                Objects.equals(offers, tender.offers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, issuer, tenderStatus, offers);
    }
}
