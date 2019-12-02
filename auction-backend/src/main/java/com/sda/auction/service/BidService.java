package com.sda.auction.service;

import com.sda.auction.dto.BidDto;

public interface BidService {

    BidDto addBid(BidDto bidDto, String userEmail);
}
