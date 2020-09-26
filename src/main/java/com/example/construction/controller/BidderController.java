package com.example.construction.controller;

import com.example.construction.model.Bidder;
import com.example.construction.model.Offer;
import com.example.construction.service.OfferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "api/bidder")
@Api(value = "BidderControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class BidderController {

    private final OfferService offerService;

    public BidderController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping(path = "/{bidderId}")
    @ApiOperation("Gets bidder offers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Bidder.class)})
    public ResponseEntity<List<Offer>> getBidderOffers(@PathVariable Long bidderId) throws NotFoundException {
        List<Offer> offers = Optional.ofNullable(offerService.getBidderOffers(bidderId)).orElseThrow(() -> new NotFoundException("There are no offers"));
        offers.forEach(offer -> offer.addIf(!offer.hasLinks(), () -> linkTo(IssuerController.class).slash(offer.getId()).withSelfRel()));
        Link link = linkTo(OfferService.class).withSelfRel();
        offers.forEach(offer -> offer.add(link));
        return ResponseEntity.ok(offers);
    }
}
