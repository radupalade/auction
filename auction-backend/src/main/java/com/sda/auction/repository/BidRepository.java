package com.sda.auction.repository;

import com.sda.auction.model.Bid;
import com.sda.auction.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository
        extends JpaRepository<Bid, Integer> {


}
