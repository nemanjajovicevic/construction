package com.example.construction.service;

import com.example.construction.model.Bidder;
import com.example.construction.repository.BidderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BidderService {
    private Logger LOG = LoggerFactory.getLogger(BidderService.class);

    private BidderRepository bidderRepository;

    @Autowired
    public void setBidderRepository(BidderRepository bidderRepository) {
        this.bidderRepository = bidderRepository;
    }

    @Transactional
    public Optional<Bidder> getBidder(Long bidderId) {
        LOG.info("Getting bidder");
        return bidderRepository.findById(bidderId);
    }
}
