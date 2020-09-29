package com.example.construction.controller;

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
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/api/bidder")
@Api(value = "BidderControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class BidderController {

    private final OfferService offerService;
    private final TenderService tenderService;
    private final BidderService bidderService;
    private final ModelMapper modelMapper;

    public BidderController(OfferService offerService, TenderService tenderService, BidderService bidderService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.tenderService = tenderService;
        this.bidderService = bidderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/{bidderId}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Gets bidder offers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Bidder.class)})
    public ResponseEntity<List<OfferDTO>> getBidderOffers(@PathVariable Long bidderId) {
        bidderService.getBidder(bidderId).orElseThrow(() -> new WebApplicationNotFoundException("Bidder doesn't exist"));
        List<Offer> offers = offerService.getBidderOffers(bidderId);
        List<OfferDTO> offersDTO = offers.stream().map(o -> modelMapper.map(o, OfferDTO.class)).collect(Collectors.toList());
        offersDTO.forEach(offer -> offer.addIf(!offer.hasLinks(), () -> linkTo(BidderController.class)
                .slash(offer.getBidder().getId())
                .slash("offers")
                .withSelfRel()));
        return ResponseEntity.ok(offersDTO);
    }

    @PostMapping(path = "/offers", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Create offers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Bidder.class)})
    public void createOffers(@RequestBody List<OfferDTO> offersDTO) {
        List<Offer> offers = offersDTO.stream().map(o -> modelMapper.map(o, Offer.class)).collect(Collectors.toList());
        offers.forEach(offerService::createOffer);
    }

    @GetMapping(path = "/{bidderId}/{tenderIds}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Gets bidder tenders offers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Bidder.class)})
    public ResponseEntity<List<OfferDTO>> getBidderTendersOffers(@PathVariable Long bidderId, @PathVariable List<Long> tenderIds) {
        bidderService.getBidder(bidderId).orElseThrow(() -> new WebApplicationNotFoundException("Bidder doesn't exist"));
        List<Offer> offers = offerService.getBidderTenderOffers(bidderId, tenderIds);
        List<OfferDTO> offersDTO = offers.stream().map(o -> modelMapper.map(o, OfferDTO.class)).collect(Collectors.toList());
        offersDTO.forEach(offer -> offer.addIf(!offer.hasLinks(), () -> linkTo(BidderController.class)
                .slash(offer.getBidder().getId())
                .slash(offer.getTender().getId())
                .withSelfRel()));
        return ResponseEntity.ok(offersDTO);
    }

    @GetMapping(path = "/tenders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Gets tenders")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Bidder.class)})
    public ResponseEntity<List<TenderDTO>> getBidderTenders() {
        List<Tender> tenders = tenderService.getTenders();
        List<TenderDTO> tendersDTO = tenders.stream().map(t -> modelMapper.map(t, TenderDTO.class)).collect(Collectors.toList());
        tendersDTO.forEach(tender -> tender.add(linkTo(BidderController.class)
                .slash("tenders")
                .withSelfRel()));
        return ResponseEntity.ok(tendersDTO);
    }
}
