package com.example.construction.controller;

import com.example.construction.dto.OfferDTO;
import com.example.construction.dto.TenderDTO;
import com.example.construction.exception.WebApplicationNotFoundException;
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
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/api/issuer")
@Api(value = "IssuerControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class IssuerController {

    private final TenderService tenderService;
    private final OfferService offerService;
    private final IssuerService issuerService;
    private final ModelMapper modelMapper;

    public IssuerController(TenderService tenderService, OfferService offerService, IssuerService issuerService, ModelMapper modelMapper) {
        this.tenderService = tenderService;
        this.offerService = offerService;
        this.issuerService = issuerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(path = "/tender", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Create tender")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Issuer.class)})
    public void createTender(@RequestBody TenderDTO tenderDTO) {
        tenderService.createTender(modelMapper.map(tenderDTO, Tender.class));
    }

    @PutMapping(path = "/offer/accept/{offerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Accept offer")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Issuer.class)})
    public void acceptOffer(@PathVariable Long offerId) {
        offerService.acceptOffer(offerId);
    }

    @GetMapping(path = "/{issuerId}/tenders/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Get issuer tender offers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Issuer.class)})
    public ResponseEntity<List<OfferDTO>> getIssuerTendersOffers(@PathVariable Long issuerId) {
        issuerService.getIssuer(issuerId).orElseThrow(() -> new WebApplicationNotFoundException("Issuer doesn't exist"));
        List<Offer> offers = offerService.getIssuerTendersOffers(issuerId);
        List<OfferDTO> offersDTO = offers.stream().map(o -> modelMapper.map(o, OfferDTO.class)).collect(Collectors.toList());
        offersDTO.forEach(offer -> offer.addIf(!offer.hasLinks(), () -> linkTo(IssuerController.class)
                .slash(issuerId)
                .slash("tenders")
                .slash("offers")
                .withSelfRel()));
        return ResponseEntity.ok(offersDTO);
    }

    @GetMapping(path = "/{issuerId}/tenders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Get issuer tenders")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Issuer.class)})
    public ResponseEntity<List<TenderDTO>> getIssuerTenders(@PathVariable Long issuerId) {
        issuerService.getIssuer(issuerId).orElseThrow(() -> new WebApplicationNotFoundException("Issuer doesn't exist"));
        List<Tender> tenders = tenderService.getTendersFromIssuer(issuerId);
        List<TenderDTO> tendersDTO = tenders.stream().map(t -> modelMapper.map(t, TenderDTO.class)).collect(Collectors.toList());
        tendersDTO.forEach(tender -> tender.addIf(!tender.hasLinks(), () -> linkTo(IssuerController.class)
                        .slash(issuerId)
                        .slash("tenders")
                        .withSelfRel()));

        return ResponseEntity.ok(tendersDTO);
    }
}
