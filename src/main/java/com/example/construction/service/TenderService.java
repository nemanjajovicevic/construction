package com.example.construction.service;

import com.example.construction.model.Tender;
import com.example.construction.repository.TenderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.construction.util.TenderStatus.OPENED;

@Service
public class TenderService {
    private Logger LOG = LoggerFactory.getLogger(TenderService.class);

    private TenderRepository tenderRepository;

    @Autowired
    public void setTenderRepository(TenderRepository tenderRepository) {
        this.tenderRepository = tenderRepository;
    }

    public List<Tender> getTenders() {
        LOG.info("Getting tenders");
        return tenderRepository.findAll();
    }

    public List<Tender> getTendersFromIssuer(Long issuerId) {
        LOG.info("Getting issuer tenders");
        return tenderRepository.findAllByIssuer_id(issuerId);
    }

    public void createTender(Tender tender) {
        LOG.info("Creating tender");
        tender.setTenderStatus(OPENED);
        tenderRepository.save(tender);
    }
}
