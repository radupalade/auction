package com.sda.auction.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class BidDto {

    private Integer id;


    @Positive
    private Integer price;

    @NotNull
    private Integer itemId;

}
