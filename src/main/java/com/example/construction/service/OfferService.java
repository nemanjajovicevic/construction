package com.example.construction.service;

import com.example.construction.model.Offer;
import com.example.construction.repository.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.construction.util.OfferStatus.*;

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
        return offerRepository.findAll()
                .stream()
                .filter(offer -> issuerId.equals(offer.getTender().getIssuer().getId()))
                .collect(Collectors.toList());
    }

    public List<Offer> getBidderOffers(Long bidderId) {
        LOG.info("Getting bidder offers");
        return offerRepository.findAll()
                .stream()
                .filter(offer -> bidderId.equals(offer.getBidder().getId()))
                .collect(Collectors.toList());
    }

    public List<Offer> getBidderTenderOffers(Long bidderId, List<Long> tenderIds) {
        LOG.info("Getting bidder tender offers");
        return offerRepository.findAll()
                .stream()
                .filter(offer -> bidderId.equals(offer.getBidder().getId())
                        && tenderIds.contains(offer.getTender().getId()))
                .collect(Collectors.toList());
    }

    public void createOffer(Offer offer) {
        LOG.info("Creating offer");
        offerRepository.save(offer);
    }

    public Offer acceptOffer(Long offerId) {
        LOG.info("Accepting offer and rejecting others");
        Offer offer = offerRepository.findById(offerId).get();
        offer.setOfferStatus(ACCEPTED);
        offerRepository.findAll()
                .stream()
                .filter(o -> o.getTender().getId()
                        .equals(offer.getTender().getId())
                        && PENDING.equals(o.getOfferStatus()))
                .forEach(o -> o.setOfferStatus(REJECTED));
        return offer;
    }
}
