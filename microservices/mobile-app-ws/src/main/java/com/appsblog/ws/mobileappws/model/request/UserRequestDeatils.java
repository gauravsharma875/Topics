package com.appsblog.ws.mobileappws.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRequestDeatils {

    @NotNull(message = "first name cant be null")
    private String firstName;
    @NotNull(message = "last name cant be null")
    private String lastName;
    @NotNull(message = "email address cant be null")
    private String emailAddress;
    @NotNull(message = "password name cant be null")
    @Size(message = "password must be greater than 5 letters", min = 5)
    private String password;

}
