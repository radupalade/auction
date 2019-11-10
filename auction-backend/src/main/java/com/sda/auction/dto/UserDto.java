package com.sda.auction.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data  //getter and setter
@EqualsAndHashCode
public class UserDto {

    private Integer id;
    @NotEmpty(message = "please insert your first name!")
    @Pattern(regexp = "[A-Za-z]+", message = "letters only!")
    private String firstName;
    @NotEmpty(message = "please insert your first name!")
    @Pattern(regexp = "[A-Za-z]+", message = "letters only!")
    private String lastName;
    @NotEmpty
    @Email(message = "{error.user.email.regex}") //TEMA de modificat peste tot
    private String email;
    @NotEmpty
    @Pattern(regexp = "((.*)[A-Z]+(.*))", message = "password should contain atleast one capital letter")
    @Size(min = 6, message = "password should be at least 6 chars long")
    private String password;
    @NotEmpty
    @Pattern(regexp = "((.*)[A-Z]+(.*))", message = "password should contain atleast one capital letter")
    @Size(min = 6, message = "Confirm password should be at least 6 chars long")
    private String confirmPassword;


}
