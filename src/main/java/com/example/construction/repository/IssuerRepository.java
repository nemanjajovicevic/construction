package com.example.construction.repository;

import com.example.construction.model.Issuer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuerRepository extends JpaRepository<Issuer, Long>  {
    Issuer findByName(String name);
}
