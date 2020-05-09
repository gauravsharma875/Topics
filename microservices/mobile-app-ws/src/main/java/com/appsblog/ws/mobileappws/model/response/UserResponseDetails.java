package com.appsblog.ws.mobileappws.model.response;

import lombok.Data;

@Data
public class UserResponseDetails {

    private String id;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
}
