package com.example.construction.dto;

import com.example.construction.util.OfferStatus;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

public class OfferDTO extends RepresentationModel<OfferDTO> {
    private Long id;
    private String name;
    private BidderDTO bidder;
    private OfferStatus offerStatus;
    private BigDecimal amount;
    private TenderDTO tender;

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

    public BidderDTO getBidder() {
        return bidder;
    }

    public void setBidder(BidderDTO bidder) {
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

    public TenderDTO getTender() {
        return tender;
    }

    public void setTender(TenderDTO tender) {
        this.tender = tender;
    }
}
