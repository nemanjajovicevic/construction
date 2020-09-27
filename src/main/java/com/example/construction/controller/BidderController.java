package com.example.construction.controller;

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
import javassist.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "api")
@Api(value = "BidderControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class BidderController {

    private final OfferService offerService;
    private final TenderService tenderService;
    private final BidderService bidderService;

    public BidderController(OfferService offerService, TenderService tenderService, BidderService bidderService) {
        this.offerService = offerService;
        this.tenderService = tenderService;
        this.bidderService = bidderService;
    }

    @GetMapping(path = "/{bidderId}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Gets bidder offers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Bidder.class)})
    public ResponseEntity<List<Offer>> getBidderOffers(@PathVariable Long bidderId) throws NotFoundException {
        bidderService.getBidder(bidderId).orElseThrow(() -> new NotFoundException("Bidder doesn't exist"));
        List<Offer> offers = offerService.getBidderOffers(bidderId);
        offers.forEach(offer -> offer.addIf(!offer.hasLinks(), () -> linkTo(BidderController.class).slash(offer.getBidder().getId()).slash("offers").withSelfRel()));
        return ResponseEntity.ok(offers);
    }

    @PostMapping(path = "/offer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Create an offer")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Bidder.class)})
    public void createOffers(@RequestBody List<Offer> offers) {
        offers.forEach(offerService::createOffer);
    }

    @GetMapping(path = "/{bidderId}/{tenderIds}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Gets bidder tenders offers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Bidder.class)})
    public ResponseEntity<List<Offer>> getBidderOffers(@PathVariable Long bidderId, @PathVariable List<Long> tenderIds) throws NotFoundException {
        bidderService.getBidder(bidderId).orElseThrow(() -> new NotFoundException("Bidder doesn't exist"));
        List<Offer> offers = offerService.getBidderTenderOffers(bidderId, tenderIds);
        offers.forEach(offer -> offer.addIf(!offer.hasLinks(), () -> linkTo(BidderController.class).slash(offer.getBidder().getId()).slash(offer.getTender().getId()).withSelfRel()));
        return ResponseEntity.ok(offers);
    }

    @GetMapping(path = "/tenders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Gets tenders")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Bidder.class)})
    public ResponseEntity<List<Tender>> getTenders() throws NotFoundException {
        List<Tender> tenders = Optional.ofNullable(tenderService.getTenders()).orElseThrow(() -> new NotFoundException("There are no tenders"));
        tenders.forEach(tender -> tender.add(linkTo(BidderController.class).slash("tenders").withSelfRel()));
        return ResponseEntity.ok(tenders);
    }
}
