package com.example.construction.service;

import com.example.construction.model.Offer;
import com.example.construction.repository.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    private Logger LOG = LoggerFactory.getLogger(OfferService.class);

    private OfferRepository offerRepository;

    @Autowired
    public void setOfferRepository(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<Offer> getOffers() {
        LOG.info("Getting offers");
        return offerRepository.findAll();
    }

    public List<Offer> getIssuerTenderOffers(Long issuerId) {
        LOG.info("Getting issuer tender offers");
        return offerRepository.getIssuerTenderOffers(issuerId);
    }

    public List<Offer> getBidderOffers(Long bidderId) {
        LOG.info("Getting bidder offers");
        return offerRepository.getBidderOffers(bidderId);
    }

    public List<Offer> getBidderTenderOffers(Long bidderId, List<Long> tenderIds) {
        LOG.info("Getting bidder tender offers");
        return offerRepository.getBidderTenderOffers(bidderId, tenderIds);
    }

    public void createOffer(Offer offer) {
        LOG.info("Creating offer");
        offerRepository.save(offer);
    }

    public void acceptOffer(Offer offer) {
        LOG.info("Accepting offer");
        offerRepository.acceptOffer(offer.getId());
    }
}
