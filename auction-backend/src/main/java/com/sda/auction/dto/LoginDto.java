package com.sda.auction.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data  //getter and setter
@EqualsAndHashCode
public class LoginDto {

    private String jwt;
    @NotEmpty
    @Email(message = "{error.user.email.regex}") //TEMA de modificat peste tot
    private String email;
    @NotEmpty
    @Pattern(regexp = "((.*)[A-Z]+(.*))", message = "{error.user.password.regex}")
    @Size(min = 6, message = "{error.user.password.min}")
    private String password;


}
