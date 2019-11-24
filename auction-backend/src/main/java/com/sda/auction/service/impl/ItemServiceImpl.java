package com.sda.auction.service.impl;

import com.sda.auction.dto.ItemDto;
import com.sda.auction.mapper.ItemMapper;
import com.sda.auction.model.Item;
import com.sda.auction.model.User;
import com.sda.auction.repository.ItemRepository;
import com.sda.auction.repository.UserRepository;
import com.sda.auction.service.ItemService;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<ItemDto> findAll() {
        List<Item> allItems = itemRepository.findAll();
        return itemMapper.convert(allItems);
    }

    @Override
    public ItemDto findById(Integer id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (!optionalItem.isPresent()) {
            throw new RuntimeException("Item with id " + id + " does not exist!");
        }

        Item item = optionalItem.get();
        return itemMapper.convert(item);
    }

    @Override
    public List<ItemDto> findAllForBidding() {
        List<Item> items = itemRepository.findAllForBidding();
        return itemMapper.convert(items);
    }

    @Override
    public ItemDto findByIdForUser(Integer id) {
        ItemDto itemDto = findById(id);
        itemDto.resetOwner();
        return itemDto;
    }
}
