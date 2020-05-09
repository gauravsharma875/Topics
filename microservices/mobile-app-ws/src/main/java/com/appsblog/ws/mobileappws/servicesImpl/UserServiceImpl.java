package com.appsblog.ws.mobileappws.servicesImpl;

import com.appsblog.ws.mobileappws.model.request.UserRequestDeatils;
import com.appsblog.ws.mobileappws.model.response.UserResponseDetails;
import com.appsblog.ws.mobileappws.services.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

import static com.appsblog.ws.mobileappws.controller.UserController.users;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserResponseDetails createUser(UserRequestDeatils requestDeatils) {
        UserResponseDetails userResponseDetails = new UserResponseDetails();

        userResponseDetails.setId(UUID.randomUUID().toString());
        userResponseDetails.setEmailAddress(requestDeatils.getEmailAddress());
        userResponseDetails.setFirstName(requestDeatils.getFirstName());
        userResponseDetails.setLastName(requestDeatils.getLastName());
        userResponseDetails.setPassword(requestDeatils.getPassword());

        if (users == null) users = new HashMap<>();
        users.put(userResponseDetails.getId(), userResponseDetails);

        return userResponseDetails;
    }
}
