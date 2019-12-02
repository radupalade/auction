package com.sda.auction.mapper;

import com.sda.auction.dto.BidDto;
import com.sda.auction.model.Bid;
import org.springframework.stereotype.Service;

@Service
public class BidMapper {

    public Bid convert(BidDto bidDto) {
        Bid result = new Bid();
        result.setPrice(bidDto.getPrice());
        return result;
    }

    public BidDto convert(Bid bid) {
        BidDto result = new BidDto();
        result.setPrice(bid.getPrice());
        result.setItemId(bid.getItem().getItemId());
        result.setId(bid.getId());
        return result;
    }
}
