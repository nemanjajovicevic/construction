package com.example.construction.controller;

import com.example.construction.dto.BidderDTO;
import com.example.construction.dto.IssuerDTO;
import com.example.construction.dto.OfferDTO;
import com.example.construction.dto.TenderDTO;
import com.example.construction.model.Bidder;
import com.example.construction.repository.BidderRepository;
import com.example.construction.repository.IssuerRepository;
import com.example.construction.repository.OfferRepository;
import com.example.construction.repository.TenderRepository;
import com.example.construction.service.BidderService;
import com.example.construction.service.IssuerService;
import com.example.construction.service.OfferService;
import com.example.construction.service.TenderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static com.example.construction.util.JsonMapper.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(BidderController.class)
public class BidderControllerUnitTest {

    @MockBean
    private IssuerService issuerService;
    @MockBean
    private IssuerRepository issuerRepository;
    @MockBean
    private BidderRepository bidderRepository;
    @MockBean
    private IssuerController issuerController;
    @MockBean
    private LinkDiscoverers linkDiscoverers;
    @MockBean
    private OfferRepository offerRepository;
    @MockBean
    private TenderRepository tenderRepository;
    @MockBean
    private BidderService bidderService;
    @MockBean
    private OfferService offerService;
    @MockBean
    private TenderService tenderService;
    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
        Bidder bidder = new Bidder();
        bidder.setId(3L);
        bidder.setName("New Bidder");
        when(bidderService.getBidder(3L)).thenReturn(java.util.Optional.of(bidder));
    }

    @Test
    public void retrieveBidderOffers() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/api/bidder/{id}/offers", 3)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createOffer() throws Exception {
        OfferDTO offerDTO = new OfferDTO();
        BidderDTO bidderDTO = new BidderDTO();
        TenderDTO tenderDTO = new TenderDTO();
        IssuerDTO issuerDTO = new IssuerDTO();
        issuerDTO.setName("Test Issuer");
        tenderDTO.setName("Test Tender");
        tenderDTO.setIssuer(issuerDTO);
        bidderDTO.setName("Test Bidder");
        offerDTO.setName("Test Offer");
        offerDTO.setAmount(new BigDecimal(10));
        offerDTO.setBidder(bidderDTO);
        offerDTO.setTender(tenderDTO);
        mvc.perform( MockMvcRequestBuilders
                .post("/api/bidder/offer")
                .characterEncoding("utf-8")
                .content(asJsonString(Collections.singletonList(offerDTO)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void retrieveBidderTendersOffers() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/api/bidder/{id}/1,2/offers",3)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void retrieveTenders() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/api/bidder/tenders")
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
