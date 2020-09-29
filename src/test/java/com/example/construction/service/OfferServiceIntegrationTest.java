package com.example.construction.service;

import com.example.construction.model.Bidder;
import com.example.construction.model.Issuer;
import com.example.construction.model.Offer;
import com.example.construction.model.Tender;
import com.example.construction.repository.BidderRepository;
import com.example.construction.repository.IssuerRepository;
import com.example.construction.repository.OfferRepository;
import com.example.construction.repository.TenderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.example.construction.util.OfferStatus.*;
import static com.example.construction.util.TenderStatus.CLOSED;
import static com.example.construction.util.TenderStatus.OPENED;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OfferServiceIntegrationTest {

    @TestConfiguration
    static class OfferServiceTestContextConfiguration {
        @Bean
        public OfferService offerService() {
            return new OfferService();
        }
    }

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private OfferService offerService;
    @Autowired
    private IssuerRepository issuerRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private BidderRepository bidderRepository;
    @Autowired
    private TenderRepository tenderRepository;

    @Before
    public void setUp() {
        Issuer firstIssuer = new Issuer();
        firstIssuer.setName("FirstMockIssuer");
        entityManager.persistAndFlush(firstIssuer);

        Bidder firstBidder = new Bidder();
        firstBidder.setName("FirstMockBidder");
        entityManager.persistAndFlush(firstBidder);

        Tender firstTender = new Tender();
        firstTender.setName("FirstMockTender");
        firstTender.setTenderStatus(OPENED);
        firstTender.setIssuer(firstIssuer);
        entityManager.persistAndFlush(firstTender);

        Offer firstOffer = new Offer();
        firstOffer.setName("FirstMockOffer");
        firstOffer.setAmount(new BigDecimal(10));
        firstOffer.setBidder(firstBidder);
        firstOffer.setOfferStatus(PENDING);
        firstOffer.setTender(firstTender);
        entityManager.persistAndFlush(firstOffer);

        Offer secondOffer = new Offer();
        secondOffer.setName("SecondMockOffer");
        secondOffer.setAmount(new BigDecimal(20));
        secondOffer.setBidder(firstBidder);
        secondOffer.setOfferStatus(PENDING);
        secondOffer.setTender(firstTender);
        entityManager.persistAndFlush(secondOffer);

        Offer thirdOffer = new Offer();
        thirdOffer.setName("ThirdMockOffer");
        thirdOffer.setAmount(new BigDecimal(30));
        thirdOffer.setBidder(firstBidder);
        thirdOffer.setOfferStatus(PENDING);
        thirdOffer.setTender(firstTender);
        entityManager.persistAndFlush(thirdOffer);
    }

    @Test
    public void retrieveIssuerTenderOffers() throws Exception {
        Issuer issuer = issuerRepository.findByName("FirstMockIssuer");
        Offer offer = offerRepository.findByName("FirstMockOffer");

        List<Offer> offers = offerService.getIssuerTendersOffers(issuer.getId());

        assertThat(offers.size() == 1);
        assertThat(offers.get(0).getTender().getId()).isEqualTo(offer.getTender().getId());
        assertThat(offers.get(0).getTender().getIssuer().getId()).isEqualTo(offer.getTender().getIssuer().getId());
    }

    @Test
    public void retrieveIssuerTenderOffers_unknownIssuer() throws Exception {
        List<Offer> offers = offerService.getIssuerTendersOffers(99L);

        assertThat(offers.isEmpty());
    }

    @Test
    public void retrieveBidderTenderOffers() throws Exception {
        Bidder bidder = bidderRepository.findByName("FirstMockBidder");
        Offer offer = offerRepository.findByName("FirstMockOffer");
        Tender tender = tenderRepository.findByName("FirstMockTender");

        List<Offer> offers = offerService.getBidderTenderOffers(bidder.getId(), Collections.singletonList(tender.getId()));

        assertThat(offers.size() == 1);
        assertThat(offers.get(0).getTender().getId()).isEqualTo(offer.getTender().getId());
        assertThat(offers.get(0).getBidder().getId()).isEqualTo(offer.getBidder().getId());
    }

    @Test
    public void retrieveBidderTenderOffers_unknownBidderUnknownTenders() throws Exception {
        List<Offer> offers = offerService.getBidderTenderOffers(99L, Collections.singletonList(99L));

        assertThat(offers.isEmpty());
    }

    @Test
    public void acceptOffer() throws Exception {
        Offer acceptedOffer = offerRepository.findByName("ThirdMockOffer");
        Offer otherOffer = offerRepository.findByName("SecondMockOffer");
        Tender tender = tenderRepository.findByName("FirstMockTender");
        assertThat(acceptedOffer.getOfferStatus()).isEqualTo(PENDING);
        assertThat(otherOffer.getOfferStatus()).isEqualTo(PENDING);
        assertThat(tender.getTenderStatus()).isEqualTo(OPENED);

        offerService.acceptOffer(acceptedOffer.getId());

        assertThat(acceptedOffer.getOfferStatus()).isEqualTo(ACCEPTED);
        assertThat(otherOffer.getOfferStatus()).isEqualTo(REJECTED);
        assertThat(tender.getTenderStatus()).isEqualTo(CLOSED);
    }
}
