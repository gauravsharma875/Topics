package com.appsblog.ws.mobileappws.utils;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserUtils {


    public String createUUID() {
        return UUID.randomUUID().toString();
    }
}
