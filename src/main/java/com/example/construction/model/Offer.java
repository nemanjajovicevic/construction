package com.example.construction.model;

import com.example.construction.util.OfferStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Offer extends RepresentationModel<Offer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    @JsonIgnore
    private Bidder bidder;
    private OfferStatus offerStatus;
    private BigDecimal amount;
    @OneToOne
    @JsonIgnore
    private Tender tender;

    public Offer() {
    }

    public Offer(Long id, String name, Bidder bidder, OfferStatus offerStatus, BigDecimal amount, Tender tender) {
        this.id = id;
        this.name = name;
        this.bidder = bidder;
        this.offerStatus = offerStatus;
        this.amount = amount;
        this.tender = tender;
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

    public Bidder getBidder() {
        return bidder;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Tender getTender() {
        return tender;
    }

    public void setTender(Tender tender) {
        this.tender = tender;
    }
}
