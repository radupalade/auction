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
    @NotEmpty(message = "{error.user.fistName.regex}")
    @Pattern(regexp = "[A-Za-z]+", message = "letters only!")
    private String firstName;
    @NotEmpty(message = "{error.user.lastName.regex}")
    @Pattern(regexp = "[A-Za-z]+", message = "letters only!")
    private String lastName;
    @NotEmpty
    @Email(message = "{error.user.email.regex}") //TEMA de modificat peste tot
    private String email;
    @NotEmpty
    @Pattern(regexp = "((.*)[A-Z]+(.*))", message = "{error.user.password.regex}")
    @Size(min = 6, message = "{error.user.password.min}")
    private String password;
    @NotEmpty
    @Pattern(regexp = "((.*)[A-Z]+(.*))", message = "password should contain atleast one capital letter")
    @Size(min = 6, message = "{error.user.confirmPassword.min}")
    private String confirmPassword;


}
