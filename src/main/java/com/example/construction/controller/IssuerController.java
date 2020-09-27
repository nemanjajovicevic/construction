package com.example.construction.controller;

import com.example.construction.model.Issuer;
import com.example.construction.model.Offer;
import com.example.construction.model.Tender;
import com.example.construction.service.IssuerService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "api")
@Api(value = "IssuerControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class IssuerController {

    private final TenderService tenderService;
    private final OfferService offerService;
    private final IssuerService issuerService;

    public IssuerController(TenderService tenderService, OfferService offerService, IssuerService issuerService) {
        this.tenderService = tenderService;
        this.offerService = offerService;
        this.issuerService = issuerService;
    }

    @PostMapping(path = "/tender", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Create tender")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Issuer.class)})
    public void createTender(@RequestBody Tender tender) {
        tenderService.createTender(tender);
    }

    @PostMapping(path = "/offer/accept", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Accept offer")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Issuer.class)})
    public void acceptOffer(@RequestBody Long offerId) {
        Offer offer = offerService.acceptOffer(offerId);
        tenderService.closeTender(offer.getTender().getId());
    }

    @GetMapping(path = "/{issuerId}/tender/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Get issuer tender offers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Issuer.class)})
    public ResponseEntity<List<Offer>> getIssuerTenderOffers(@PathVariable Long issuerId) throws NotFoundException {
        issuerService.getIssuer(issuerId).orElseThrow(() -> new NotFoundException("Issuer doesn't exist"));
        List<Offer> offers = offerService.getIssuerTenderOffers(issuerId);
        offers.forEach(offer -> offer.addIf(!offer.hasLinks(), () -> linkTo(IssuerController.class).slash(issuerId).slash("tender").slash("offers").withSelfRel()));
        return ResponseEntity.ok(offers);
    }

    @GetMapping(path = "/{issuerId}/tenders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Get issuer tenders")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Issuer.class)})
    public ResponseEntity<List<Tender>> getIssuerTenders(@PathVariable Long issuerId) throws NotFoundException {
        issuerService.getIssuer(issuerId).orElseThrow(() -> new NotFoundException("Issuer doesn't exist"));
        List<Tender> tenders = tenderService.getTendersFromIssuer(issuerId);
        tenders.forEach(tender -> tender.addIf(!tender.hasLinks(), () -> linkTo(IssuerController.class).slash(issuerId).slash("tenders").withSelfRel()));
        return ResponseEntity.ok(tenders);
    }
}
