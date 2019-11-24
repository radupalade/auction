package com.sda.auction.service;

import com.sda.auction.dto.HeaderDto;
import com.sda.auction.dto.LoginDto;
import com.sda.auction.dto.UserDto;
import com.sda.auction.model.User;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public interface SecurityService {


    boolean passwordMatch(LoginDto userDto, User user);

    LoginDto createDtoWithJwt(User user);

    boolean isValid(ServletRequest servletRequest);

    void setEmailOn(ServletRequest servletRequest);

    HeaderDto getHeaderDtoFrom(HttpServletRequest request);


    void addUserRoles(User user);
}
