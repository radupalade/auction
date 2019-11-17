package com.sda.auction.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sda.auction.dto.ErrorResponseDto;
import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccesDeniedHandler {

    public void reply(ServletResponse servletResponse) throws IOException {
        ErrorResponseDto responseDto = new ErrorResponseDto(401, "Unauthorized Access !!!!");
        byte[] response = responseDto.getBytes();
        servletResponse.getOutputStream().write(response);

        ((HttpServletResponse) servletResponse).setHeader("Content-Type", "application/json");
        ((HttpServletResponse) servletResponse).setStatus(401);
    }
}
