package com.example.construction.controller;

import com.example.construction.converter.EntityConverter;
import com.example.construction.dto.OfferDTO;
import com.example.construction.dto.TenderDTO;
import com.example.construction.exception.WebApplicationNotFoundException;
import com.example.construction.model.Bidder;
import com.example.construction.model.Offer;
import com.example.construction.model.Tender;
import com.example.construction.service.BidderService;
import com.example.construction.service.OfferService;
import com.example.construction.service.TenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "api")
@Api(value = "BidderControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class BidderController {

    private final OfferService offerService;
    private final TenderService tenderService;
    private final BidderService bidderService;
    private final EntityConverter entityConverter;

    public BidderController(OfferService offerService, TenderService tenderService, BidderService bidderService, EntityConverter entityConverter) {
        this.offerService = offerService;
        this.tenderService = tenderService;
        this.bidderService = bidderService;
        this.entityConverter = entityConverter;
    }

    @GetMapping(path = "/{bidderId}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Gets bidder offers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Bidder.class)})
    public ResponseEntity<List<OfferDTO>> getBidderOffers(@PathVariable Long bidderId) {
        bidderService.getBidder(bidderId).orElseThrow(() -> new WebApplicationNotFoundException("Bidder doesn't exist"));
        List<Offer> offers = offerService.getBidderOffers(bidderId);
        List<OfferDTO> offersDTO = entityConverter.convertOffers(offers);
        offersDTO.forEach(offer -> offer.addIf(!offer.hasLinks(), () -> linkTo(BidderController.class)
                .slash(offer.getBidder().getId())
                .slash("offers")
                .withSelfRel()));
        return ResponseEntity.ok(offersDTO);
    }

    @PostMapping(path = "/offer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Create an offer")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Bidder.class)})
    public void createOffers(@RequestBody List<OfferDTO> offersDTO) {
        List<Offer> offers = entityConverter.convertOffersDTO(offersDTO);
        offers.forEach(offerService::createOffer);
    }

    @GetMapping(path = "/{bidderId}/{tenderIds}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Gets bidder tenders offers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Bidder.class)})
    public ResponseEntity<List<OfferDTO>> getBidderOffers(@PathVariable Long bidderId, @PathVariable List<Long> tenderIds) {
        bidderService.getBidder(bidderId).orElseThrow(() -> new WebApplicationNotFoundException("Bidder doesn't exist"));
        List<Offer> offers = offerService.getBidderTenderOffers(bidderId, tenderIds);
        List<OfferDTO> offersDTO = entityConverter.convertOffers(offers);
        offersDTO.forEach(offer -> offer.addIf(!offer.hasLinks(), () -> linkTo(BidderController.class)
                .slash(offer.getBidder().getId())
                .slash(offer.getTender().getId())
                .withSelfRel()));
        return ResponseEntity.ok(offersDTO);
    }

    @GetMapping(path = "/tenders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Gets tenders")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Bidder.class)})
    public ResponseEntity<List<TenderDTO>> getTenders() {
        List<Tender> tenders = tenderService.getTenders();
        List<TenderDTO> tendersDTO = entityConverter.convertTenders(tenders);
        tendersDTO.forEach(tender -> tender.add(linkTo(BidderController.class)
                .slash("tenders")
                .withSelfRel()));
        return ResponseEntity.ok(tendersDTO);
    }
}
