package com.sda.auction.controller;

import com.sda.auction.dto.BidDto;
import com.sda.auction.dto.ItemDto;
import com.sda.auction.service.BidService;
import com.sda.auction.service.ItemService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/bid")
public class BidItemController {


    @Autowired
    private BidService bidService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BidDto> newBid(@Valid @RequestBody BidDto bidDto,
                                         HttpServletRequest request) {
        String userEmail = (String) request.getAttribute("userEmail");

        BidDto result = bidService.addBid(bidDto, userEmail);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
