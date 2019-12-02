package com.sda.auction.mapper;

import com.sda.auction.dto.ItemDto;
import com.sda.auction.model.Item;
import com.sda.auction.util.DateConverter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemMapper {

    @Autowired
    private DateConverter dateConverter;

    public Item convert(ItemDto itemDto) throws ParseException {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setCategory(itemDto.getCategory());
        item.setStartingPrice(itemDto.getStartingPrice());
        item.setDescription(itemDto.getDescription());

        Date startDate = dateConverter.parse(itemDto.getStartDate());
        item.setStartDate(startDate);

        Date endDate = dateConverter.parse(itemDto.getEndDate());
        item.setEndDate(endDate);

        String photo = Base64.getEncoder().encodeToString(itemDto.getPhoto().getBytes());
        item.setPhoto(photo);

        return item;
    }

    public ItemDto convert(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setName(item.getName());
        itemDto.setCategory(item.getCategory());
        itemDto.setStartingPrice(item.getStartingPrice());
        itemDto.setDescription(item.getDescription());
        itemDto.setCurrentPrice(item.getCurrentPrice());

        String startDate = dateConverter.format(item.getStartDate());
        itemDto.setStartDate(startDate);

        String endDate = dateConverter.format(item.getEndDate());
        itemDto.setEndDate(endDate);

        itemDto.setId(item.getItemId());

        String photo = new String(Base64.getDecoder().decode(item.getPhoto().getBytes()));
        itemDto.setPhoto(photo);

        itemDto.setOwner(item.getOwnersName());
        return itemDto;
    }

    public List<ItemDto> convert(List<Item> allItems) {
        //old way:
//		List<ItemDto> result = new ArrayList<>();
//		for (Item item : allItems) {
//			result.add(convert(item));
//		}
//		return result;

        //java 8, same thing as above:
        return allItems.stream().map(this::convert).collect(Collectors.toList());
    }

    public ItemDto convert(Item item, String userEmail) {
        ItemDto result = convert(item); // standard

        //pasul aditional: vedem ultimul bid al userului logat
        Integer bidValue = item.getHighestBidOf(userEmail);
        result.setMyLastBidValue(bidValue);

        return result;
    }
}
