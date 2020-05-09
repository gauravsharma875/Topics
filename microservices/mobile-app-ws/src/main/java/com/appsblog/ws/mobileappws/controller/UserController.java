package com.appsblog.ws.mobileappws.controller;

import com.appsblog.ws.mobileappws.exceptions.UserServiceException;
import com.appsblog.ws.mobileappws.model.UpdateUserDetails;
import com.appsblog.ws.mobileappws.model.request.UserRequestDeatils;
import com.appsblog.ws.mobileappws.model.response.UserResponseDetails;
import com.appsblog.ws.mobileappws.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static org.springframework.util.CollectionUtils.isEmpty;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    public static Map<String, UserResponseDetails> users;

    @GetMapping
    public String getUsers(@RequestParam(value = "pageName",
            defaultValue = "retention", required = false) String pageName) {

        if (true) throw new UserServiceException("user services exception");
        return "get user is called with id as : " + pageName;
    }

    @GetMapping(path = "/{userId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponseDetails> getUser(@PathVariable String userId) {

        if (!isEmpty(users) && users.containsKey(userId)) {
            return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<UserResponseDetails> createUser(
            @Valid @RequestBody UserRequestDeatils requestDeatils) {

        UserResponseDetails userResponseDetails = userService.createUser(requestDeatils);
        return new ResponseEntity<>(userResponseDetails, HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserResponseDetails updateUser(@PathVariable String userId, @RequestBody UpdateUserDetails updateUserDetails) {

        UserResponseDetails responseDetails = users.get(userId);
        responseDetails.setFirstName(updateUserDetails.getFirstName());
        responseDetails.setLastName(updateUserDetails.getLastName());
        users.put(userId, responseDetails);
        return responseDetails;
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId) {
        users.remove(userId);
        return ResponseEntity.noContent().build();
    }

}
