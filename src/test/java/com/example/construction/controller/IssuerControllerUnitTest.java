package com.example.construction.controller;

import com.example.construction.dto.IssuerDTO;
import com.example.construction.dto.TenderDTO;
import com.example.construction.repository.BidderRepository;
import com.example.construction.repository.IssuerRepository;
import com.example.construction.repository.OfferRepository;
import com.example.construction.repository.TenderRepository;
import com.example.construction.service.BidderService;
import com.example.construction.service.IssuerService;
import com.example.construction.service.OfferService;
import com.example.construction.service.TenderService;
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

import static com.example.construction.util.JsonMapper.asJsonString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(IssuerController.class)
public class IssuerControllerUnitTest {

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

    @Test
    public void createTender() throws Exception {
        TenderDTO tenderDTO = new TenderDTO();
        tenderDTO.setName("Test Tender");
        IssuerDTO issuerDTO = new IssuerDTO();
        issuerDTO.setId(1L);
        tenderDTO.setIssuer(issuerDTO);
        mvc.perform( MockMvcRequestBuilders
                .post("/api/issuer/tender")
                .characterEncoding("utf-8")
                .content(asJsonString(tenderDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void retrieveIssuerTenderOffers() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/api/issuer/{id}/tender/offers",1)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void acceptOffer() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .put("/api/issuer/offer/accept/{id}", 2)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void retrieveIssuerTenders() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/issuer/{id}/tenders",1)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
