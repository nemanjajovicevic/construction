package com.example.construction.repository;

import com.example.construction.model.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TenderRepository extends JpaRepository<Tender, Long> {

    @Query("SELECT t FROM Tender t WHERE t.issuer.id = :issuerId")
    List<Tender> getIssuerTenders(@Param("issuerId") Long issuerId);
}
