package com.sda.auction.repository;


import com.sda.auction.model.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository
        extends JpaRepository<Item, Integer> {

    @Query("SELECT item FROM Item item where current_date between item.startDate and item.endDate")
    List<Item> findAllForBidding();

    Item findByItemId(Integer itemId);
}
