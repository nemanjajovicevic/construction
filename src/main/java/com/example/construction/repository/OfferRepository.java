package com.example.construction.repository;

import com.example.construction.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("SELECT o FROM Offer o WHERE o.tender.id IN (SELECT t.id FROM Tender t WHERE t.issuer.id = :issuerId)")
    List<Offer> getIssuerTenderOffers(@Param("issuerId") Long issuerId);

    @Query("SELECT o FROM Offer o WHERE o.bidder.id = :bidderId")
    List<Offer> getBidderOffers(@Param("bidderId") Long bidderId);

    @Query("SELECT o FROM Offer o WHERE o.bidder.id = :bidderId AND o.tender.id IN (:tenderIds)")
    List<Offer> getBidderTenderOffers(@Param("bidderId") Long bidderId, @Param("tenderIds") List<Long> tenderIds);

    @Query("UPDATE Offer o SET o.offerStatus = 2 WHERE o.id = :offerId")
    void acceptOffer(@Param("offerId") Long offerId);
}
