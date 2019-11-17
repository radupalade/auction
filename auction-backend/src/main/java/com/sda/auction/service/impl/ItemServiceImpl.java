package com.sda.auction.service.impl;

import com.sda.auction.dto.ItemDto;
import com.sda.auction.mapper.ItemMapper;
import com.sda.auction.model.Item;
import com.sda.auction.model.User;
import com.sda.auction.repository.ItemRepository;
import com.sda.auction.repository.UserRepository;
import com.sda.auction.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public ItemDto addItem(ItemDto itemDto, String ownerEmail) throws ParseException {
        User owner = userRepository.findByEmail(ownerEmail);

        Item item = itemMapper.convert(itemDto);
        item.setOwner(owner);

        Item savedItem = itemRepository.save(item);

        return itemMapper.convert(savedItem);
    }
}
