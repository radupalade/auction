package com.sda.auction.service.impl;

import com.sda.auction.dto.BidDto;
import com.sda.auction.mapper.BidMapper;
import com.sda.auction.mapper.HeaderMapper;
import com.sda.auction.model.Bid;
import com.sda.auction.model.Item;
import com.sda.auction.model.User;
import com.sda.auction.repository.BidRepository;
import com.sda.auction.repository.ItemRepository;
import com.sda.auction.repository.UserRepository;
import com.sda.auction.service.BidService;
import com.sda.auction.validator.BidValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.ccache.FileCredentialsCache;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BidMapper bidMapper;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private BidValidator bidValidator;

    @Override
    public BidDto addBid(BidDto bidDto, String userEmail) {
        Item item = itemRepository.findByItemId(bidDto.getItemId());

        boolean isValid = bidValidator.isValid(bidDto, item);
        if (!isValid) {
            throw new RuntimeException("Your bid is too small!");
        }

        User user = userRepository.findByEmail(userEmail);
        Bid bid = bidMapper.convert(bidDto);

        bid.setUser(user);
        bid.setItem(item);

        Bid savedBid = bidRepository.save(bid);
        return bidMapper.convert(savedBid);
    }
}
