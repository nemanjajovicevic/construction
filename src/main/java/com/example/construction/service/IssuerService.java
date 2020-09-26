package com.example.construction.service;

import com.example.construction.model.Issuer;
import com.example.construction.repository.IssuerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssuerService {
    private Logger LOG = LoggerFactory.getLogger(IssuerService.class);

    private IssuerRepository issuerRepository;

    @Autowired
    public void setIssuerRepository(IssuerRepository issuerRepository) {
        this.issuerRepository = issuerRepository;
    }

    public List<Issuer> getIssuers() {
        LOG.info("Getting Issuers");
        return issuerRepository.findAll();
    }
}
