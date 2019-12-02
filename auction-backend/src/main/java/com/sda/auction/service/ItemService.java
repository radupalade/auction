package com.sda.auction.service;

import com.sda.auction.dto.ItemDto;
import java.text.ParseException;
import java.util.List;

public interface ItemService {

    ItemDto addItem(ItemDto itemDto, String ownerEmail) throws ParseException;


    List<ItemDto> findAll();

    ItemDto findByIdFor(Integer id, String userEmail);

    List<ItemDto> findAllForBidding();

    ItemDto findByIdForUser(Integer id, String email);
}
