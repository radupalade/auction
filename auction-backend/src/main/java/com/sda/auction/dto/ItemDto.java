package com.sda.auction.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

@Data  //getter and setter
@EqualsAndHashCode
public class ItemDto {

    private Integer id;
    @NotEmpty(message = "please insert item`s name")
    @Pattern(regexp = "[A-Za-z]+", message = "letters only!")
    private String name;
    @NotEmpty(message = "please insert item`s description")
    private String description;
    @Positive  // > 0
    private int startingPrice;
    @NotEmpty
    private String category;
    @NotEmpty
    private String startDate;
    @NotEmpty
    private String endDate;


}
