package com.sda.auction.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {

    private int code; //error code
    private String message; //mesajul de eroare

    @JsonIgnore
    public byte[] getBytes() throws JsonProcessingException {
        String serialized = new ObjectMapper().writeValueAsString(this);
        return serialized.getBytes();
    }
}
