package com.example.construction.repository;

import com.example.construction.model.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenderRepository extends JpaRepository<Tender, Long> {
    List<Tender> findAllByIssuer_id(Long issuerId);
    Tender findByName(String name);
}
