package com.example.UserApp.controller;

import com.example.UserApp.model.request.CreateUserRequestModel;
import com.example.UserApp.model.response.CreateUserResponseModel;
import com.example.UserApp.service.UserService;
import com.example.UserApp.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private Environment env;

    @GetMapping
    public String users() {
        return "list of all users :" + env.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDeatils) {

        UserDto userDto = new ModelMapper().map(userDeatils, UserDto.class);
        UserDto userServiceUser = userService.createUser(userDto);

        CreateUserResponseModel createUserResponseModel=new ModelMapper().map(userServiceUser, CreateUserResponseModel.class);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(createUserResponseModel);
        //return new ResponseEntity<>(createUserResponseModel, HttpStatus.CREATED);

    }
}
