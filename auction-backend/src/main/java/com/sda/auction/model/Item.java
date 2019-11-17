package com.sda.auction.model;


import lombok.Data;

import javax.persistence.*;
import java.text.Bidi;
import java.util.*;

@Entity
@Table(name = "item")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int id;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
    private Set<Bid> bids = new HashSet<>();

//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "user_role",
//			joinColumns = @JoinColumn(name = "user_id"),
//			inverseJoinColumns = @JoinColumn(name = "role_id"))
//	private Set<Role> roles = new HashSet<>();


}