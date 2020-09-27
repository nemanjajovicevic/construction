package com.example.construction.converter;

import com.example.construction.dto.OfferDTO;
import com.example.construction.dto.TenderDTO;
import com.example.construction.model.Offer;
import com.example.construction.model.Tender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntityConverter {
    public Offer convertToOffer(OfferDTO offerDTO) {
        Offer offer = new Offer();
        offer.setAmount(offerDTO.getAmount());
        offer.setTender(offerDTO.getTender());
        offer.setName(offerDTO.getName());
        offer.setBidder(offerDTO.getBidder());
        return offer;
    }

    public Tender convertToTender(TenderDTO tenderDTO) {
        Tender tender = new Tender();
        tender.setId(tenderDTO.getId());
        tender.setName(tenderDTO.getName());
        tender.setIssuer(tenderDTO.getIssuer());
        return tender;
    }

    public TenderDTO convertToTenderDTO(Tender tender) {
        TenderDTO tenderDTO = new TenderDTO();
        tenderDTO.setId(tender.getId());
        tenderDTO.setName(tender.getName());
        tenderDTO.setIssuer(tender.getIssuer());
        return tenderDTO;
    }

    public OfferDTO convertToOfferDTO(Offer offer) {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setId(offer.getId());
        offerDTO.setName(offer.getName());
        offerDTO.setAmount(offer.getAmount());
        offerDTO.setBidder(offer.getBidder());
        offerDTO.setTender(offer.getTender());
        return offerDTO;
    }

    public List<TenderDTO> convertTenders(List<Tender> tenders) {
        List<TenderDTO> tendersDTO = new ArrayList<>();
        tenders.forEach(t -> tendersDTO.add(convertToTenderDTO(t)));
        return tendersDTO;
    }

    public List<OfferDTO> convertOffers(List<Offer> offers) {
        List<OfferDTO> offersDTO = new ArrayList<>();
        offers.forEach(o -> offersDTO.add(convertToOfferDTO(o)));
        return offersDTO;
    }

    public List<Offer> convertOffersDTO(List<OfferDTO> offersDTO) {
        List<Offer> offers = new ArrayList<>();
        offersDTO.forEach(o -> offers.add(convertToOffer(o)));
        return offers;
    }
}
