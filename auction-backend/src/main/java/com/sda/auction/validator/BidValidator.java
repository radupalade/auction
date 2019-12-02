package com.sda.auction.validator;

import com.sda.auction.dto.BidDto;
import com.sda.auction.model.Item;
import org.springframework.stereotype.Component;

@Component
public class BidValidator {

    public boolean isValid(BidDto bidDto, Item item) {
        Integer bidPrice = bidDto.getPrice();
        Integer currentItemPrice = item.getCurrentPrice();
        if (item.hasNoBids()) {
            return bidPrice >= currentItemPrice;
        }
        return bidPrice > currentItemPrice;
    }

}
