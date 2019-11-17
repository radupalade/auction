package com.sda.auction.service;

import com.sda.auction.dto.LoginDto;
import com.sda.auction.dto.UserDto;
import com.sda.auction.model.User;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletRequest;

public interface SecurityService {

    boolean passwordMatch(LoginDto userDto, User user);

    LoginDto createDtoWithJwt(User user);

    boolean isValid(ServletRequest servletRequest);

}
