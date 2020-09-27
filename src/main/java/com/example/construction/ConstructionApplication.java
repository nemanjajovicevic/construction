package com.example.construction;

import com.example.construction.model.Bidder;
import com.example.construction.model.Issuer;
import com.example.construction.repository.BidderRepository;
import com.example.construction.repository.IssuerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ConstructionApplication implements CommandLineRunner {

    private IssuerRepository issuerRepository;
    private BidderRepository bidderRepository;

    @Autowired
    public void issuerRepository(IssuerRepository issuerRepository) {
        this.issuerRepository = issuerRepository;
    }
    @Autowired
    public void bidderRepository(BidderRepository bidderRepository) {
        this.bidderRepository = bidderRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConstructionApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        List<Issuer> issuers = new ArrayList<>();
        Issuer firstIssuerInitData = new Issuer();
        firstIssuerInitData.setName("First issuer");
        Issuer secondIssuerInitData = new Issuer();
        secondIssuerInitData.setName("Second issuer");
        issuers.add(firstIssuerInitData);
        issuers.add(secondIssuerInitData);

        List<Bidder> bidders = new ArrayList<>();
        Bidder firstBidderInitData = new Bidder();
        firstBidderInitData.setName("First bidder");
        Bidder secondBidderInitData = new Bidder();
        secondBidderInitData.setName("Second bidder");
        Bidder thirdBidderInitData = new Bidder();
        thirdBidderInitData.setName("Third bidder");
        bidders.add(firstBidderInitData);
        bidders.add(secondBidderInitData);
        bidders.add(thirdBidderInitData);

        issuerRepository.saveAll(issuers);
        bidderRepository.saveAll(bidders);
    }
}
