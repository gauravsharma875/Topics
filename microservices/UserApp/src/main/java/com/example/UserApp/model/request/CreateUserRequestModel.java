package com.example.UserApp.model.request;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserRequestModel {

    @NotNull(message = "first name cannot be null")
    private String firstName;
    private String lastName;
    private String password;
    @Email
    private String email;
}
