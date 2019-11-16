package com.sda.auction.controller;

import com.sda.auction.dto.LoginDto;
import com.sda.auction.dto.UserDto;
import com.sda.auction.service.UserService;
import com.sda.auction.validator.UserDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
public class Login {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDtoValidator userDtoValidator;

    @GetMapping
    public ResponseEntity<String> get() {
        return new ResponseEntity<String>("Hello World", HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<LoginDto> post(@Valid @RequestBody LoginDto loginDto) {
        LoginDto loginDtoResult = userService.login(loginDto);


        return new ResponseEntity<>(loginDtoResult, HttpStatus.OK);
    }


}
