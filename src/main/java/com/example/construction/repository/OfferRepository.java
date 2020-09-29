package com.example.construction.repository;

import com.example.construction.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByBidder_id(Long bidderId);
    List<Offer> findAllByTender_id(Long tenderId);
    Offer findByName(String name);
}
