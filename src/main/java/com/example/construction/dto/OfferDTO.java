package com.example.construction.dto;

import com.example.construction.model.Bidder;
import com.example.construction.model.Tender;
import com.example.construction.util.OfferStatus;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

public class OfferDTO extends RepresentationModel<OfferDTO> {
    private Long id;
    private String name;
    private Bidder bidder;
    private OfferStatus offerStatus;
    private BigDecimal amount;
    private Tender tender;

    public OfferDTO() {
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
