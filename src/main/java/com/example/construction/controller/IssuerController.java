package com.example.construction.controller;

import com.example.construction.model.Issuer;
import com.example.construction.service.IssuerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/api/construction/issuer")
@Api(value = "IssuerControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class IssuerController {

    private final IssuerService issuerService;

    public IssuerController(IssuerService issuerService) {
        this.issuerService = issuerService;
    }

    @GetMapping
    @ApiOperation("Gets all issuers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Issuer.class)})
    public ResponseEntity<List<Issuer>> getIssuers() throws NotFoundException {
        List<Issuer> issuers = Optional.ofNullable(issuerService.getIssuers()).orElseThrow(() -> new NotFoundException("There are no issuers"));
        issuers.forEach(issuer -> issuer.addIf(!issuer.hasLinks(), () -> linkTo(IssuerController.class).slash(issuer.getId()).withSelfRel()));
        Link link = linkTo(IssuerService.class).withSelfRel();
        issuers.forEach(issuer -> issuer.add(link));
        return ResponseEntity.ok(issuers);
    }
}
