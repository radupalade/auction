package com.sda.auction.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "item")
@Data
@EqualsAndHashCode(exclude = "user")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int itemId;

    @Column
    private String name;
    @Column
    private String description;
    @Column
    private int startingPrice;
    @Column
    private Date startDate;
    @Column
    private Date endDate;

    @Column
    private String category;

    @Lob
    @Column
    private String photo;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)

    private Set<Bid> bids = new HashSet<>();

    public String getOwnersName() {
        return owner.getFriendlyName();
    }

    public Integer getCurrentPrice() {
        if (bids.isEmpty()) {
            return startingPrice;
        }
        Bid maxBid = Collections.max(bids);
        return maxBid.getPrice();
    }

    public boolean hasNoBids() {
        return bids.isEmpty();
    }

    public Integer getHighestBidOf(String userEmail) {
        //varianta clasica, pe care o stiti:
//		int maxBidValue = -1;
//		for (Bid each : bids) {
//			if (each.getUser().getEmail().compareTo(userEmail) == 0) {
//				if (maxBidValue < each.getPrice()) {
//					maxBidValue = each.getPrice();
//				}
//			}
//		}
//		return maxBidValue;

        // varianta ce foloseste java 8 si face acelasi lucru ca in codul de mai sus:
        Optional<Bid> maxBid = bids.stream()
                .filter(bid -> bid.getUserEmail().compareTo(userEmail) == 0)
                .max(Comparator.comparing(Bid::getPrice));
        return maxBid.isPresent() ? maxBid.get().getPrice() : -1;


    }
}
