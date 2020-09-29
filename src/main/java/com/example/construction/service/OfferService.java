package com.example.construction.service;

import com.example.construction.model.Offer;
import com.example.construction.model.Tender;
import com.example.construction.repository.OfferRepository;
import com.example.construction.repository.TenderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.construction.util.OfferStatus.*;
import static com.example.construction.util.TenderStatus.CLOSED;

@Service
public class OfferService {
    private Logger LOG = LoggerFactory.getLogger(OfferService.class);

    private OfferRepository offerRepository;
    private TenderRepository tenderRepository;

    @Autowired
    public void setOfferRepository(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }
    @Autowired
    public void tenderRepository(TenderRepository tenderRepository) {
        this.tenderRepository = tenderRepository;
    }

    @Transactional
    public List<Offer> getOffers() {
        LOG.info("Getting offers");
        return offerRepository.findAll();
    }

    @Transactional
    public List<Offer> getIssuerTenderOffers(Long issuerId) {
        LOG.info("Getting issuer tender offers");
        List<Tender> tenders = tenderRepository.findAll()
                .stream()
                .filter(t -> issuerId.equals(t.getIssuer().getId()))
                .collect(Collectors.toList());
        List<Offer> offers = new ArrayList<>();
        tenders.forEach(t -> offers.addAll(offerRepository.findAllByTender_id(t.getId())));
        return offers;
    }

    @Transactional
    public List<Offer> getBidderOffers(Long bidderId) {
        LOG.info("Getting bidder offers");
        return offerRepository.findAllByBidder_id(bidderId);
    }

    @Transactional
    public List<Offer> getBidderTenderOffers(Long bidderId, List<Long> tenderIds) {
        LOG.info("Getting bidder tender offers");

        return offerRepository.findAllByBidder_id(bidderId)
                .stream()
                .filter(o -> tenderIds.contains(o.getTender().getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createOffer(Offer offer) {
        LOG.info("Creating offer");
        offer.setOfferStatus(PENDING);
        offerRepository.save(offer);
    }

    @Transactional
    public void acceptOffer(Long offerId) {
        LOG.info("Accepting offer and rejecting others");
        Offer offer = offerRepository.findById(offerId).get();
        offer.setOfferStatus(ACCEPTED);
        Tender tender = tenderRepository.getOne(offer.getTender().getId());
        List<Offer> offersToReject = offerRepository.findAllByTender_id(tender.getId())
                .stream()
                .filter(o -> PENDING.equals(o.getOfferStatus()))
                .collect(Collectors.toList());
        offersToReject.forEach(o -> o.setOfferStatus(REJECTED));
        offerRepository.saveAll(offersToReject);
        LOG.info("Closing tender");
        tender.setTenderStatus(CLOSED);
        tenderRepository.save(tender);
    }
}
