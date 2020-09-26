package com.example.construction;

import com.example.construction.model.Bidder;
import com.example.construction.model.Issuer;
import com.example.construction.model.Offer;
import com.example.construction.model.Tender;
import com.example.construction.repository.BidderRepository;
import com.example.construction.repository.IssuerRepository;
import com.example.construction.repository.OfferRepository;
import com.example.construction.repository.TenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.construction.util.OfferStatus.*;
import static com.example.construction.util.TenderStatus.CLOSED;
import static com.example.construction.util.TenderStatus.OPENED;

@SpringBootApplication
public class ConstructionApplication implements CommandLineRunner {

    private IssuerRepository issuerRepository;
    private BidderRepository bidderRepository;
    private TenderRepository tenderRepository;
    private OfferRepository offerRepository;

    @Autowired
    public void issuerRepository(IssuerRepository issuerRepository) {
        this.issuerRepository = issuerRepository;
    }
    @Autowired
    public void bidderRepository(BidderRepository bidderRepository) {
        this.bidderRepository = bidderRepository;
    }
    @Autowired
    public void tenderRepository(TenderRepository tenderRepository) {
        this.tenderRepository = tenderRepository;
    }
    @Autowired
    public void offerRepository(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
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

        List<Tender> tenders = new ArrayList<>();
        Tender firstTenderInitData = new Tender();
        firstTenderInitData.setIssuer(firstIssuerInitData);
        firstTenderInitData.setName("First tender");
        firstTenderInitData.setTenderStatus(OPENED);
        Tender secondTenderInitData = new Tender();
        secondTenderInitData.setIssuer(secondIssuerInitData);
        secondTenderInitData.setName("Second tender");
        secondTenderInitData.setTenderStatus(CLOSED);
        Tender thirdTenderInitData = new Tender();
        thirdTenderInitData.setIssuer(secondIssuerInitData);
        thirdTenderInitData.setName("Third tender");
        thirdTenderInitData.setTenderStatus(OPENED);
        tenders.add(firstTenderInitData);
        tenders.add(secondTenderInitData);
        tenders.add(thirdTenderInitData);

        List<Offer> offers = new ArrayList<>();
        Offer firstOfferInitData = new Offer();
        firstOfferInitData.setBidder(firstBidderInitData);
        firstOfferInitData.setTender(firstTenderInitData);
        firstOfferInitData.setName("First offer");
        firstOfferInitData.setAmount(new BigDecimal(10));
        firstOfferInitData.setOfferStatus(PENDING);
        Offer secondOfferInitData = new Offer();
        secondOfferInitData.setBidder(secondBidderInitData);
        secondOfferInitData.setTender(secondTenderInitData);
        secondOfferInitData.setName("Second offer");
        secondOfferInitData.setAmount(new BigDecimal(20));
        secondOfferInitData.setOfferStatus(PENDING);
        Offer thirdOfferInitData = new Offer();
        thirdOfferInitData.setBidder(secondBidderInitData);
        thirdOfferInitData.setTender(secondTenderInitData);
        thirdOfferInitData.setName("Third offer");
        thirdOfferInitData.setAmount(new BigDecimal(30));
        thirdOfferInitData.setOfferStatus(ACCEPTED);
        Offer fourthOfferInitData = new Offer();
        fourthOfferInitData.setBidder(thirdBidderInitData);
        fourthOfferInitData.setTender(thirdTenderInitData);
        fourthOfferInitData.setName("Fourth offer");
        fourthOfferInitData.setAmount(new BigDecimal(40));
        fourthOfferInitData.setOfferStatus(PENDING);
        Offer fifthOfferInitData = new Offer();
        fifthOfferInitData.setBidder(thirdBidderInitData);
        fifthOfferInitData.setTender(thirdTenderInitData);
        fifthOfferInitData.setName("Fifth offer");
        fifthOfferInitData.setAmount(new BigDecimal(50));
        fifthOfferInitData.setOfferStatus(ACCEPTED);
        Offer sixthOfferInitData = new Offer();
        sixthOfferInitData.setBidder(thirdBidderInitData);
        sixthOfferInitData.setTender(thirdTenderInitData);
        sixthOfferInitData.setName("Sixth offer");
        sixthOfferInitData.setAmount(new BigDecimal(60));
        sixthOfferInitData.setOfferStatus(REJECTED);
        offers.add(fifthOfferInitData);
        offers.add(secondOfferInitData);
        offers.add(thirdOfferInitData);
        offers.add(fourthOfferInitData);
        offers.add(fifthOfferInitData);
        offers.add(sixthOfferInitData);

        issuerRepository.saveAll(issuers);
        bidderRepository.saveAll(bidders);
        tenderRepository.saveAll(tenders);
        offerRepository.saveAll(offers);

    }
}
