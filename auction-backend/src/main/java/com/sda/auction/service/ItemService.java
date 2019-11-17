package com.sda.auction.service;

import com.sda.auction.dto.ItemDto;
import com.sda.auction.dto.LoginDto;
import com.sda.auction.dto.UserDto;
import com.sda.auction.model.User;
import org.springframework.stereotype.Component;

import java.text.ParseException;

public interface ItemService {

    ItemDto addItem(ItemDto itemDto, String ownerEmail) throws ParseException;


}
