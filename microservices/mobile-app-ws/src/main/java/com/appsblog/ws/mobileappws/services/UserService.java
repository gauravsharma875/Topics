package com.appsblog.ws.mobileappws.services;

import com.appsblog.ws.mobileappws.model.request.UserRequestDeatils;
import com.appsblog.ws.mobileappws.model.response.UserResponseDetails;

public interface UserService {

    UserResponseDetails createUser(UserRequestDeatils requestDeatils);
}
