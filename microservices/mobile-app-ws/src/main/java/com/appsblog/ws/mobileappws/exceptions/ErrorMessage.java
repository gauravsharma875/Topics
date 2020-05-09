package com.appsblog.ws.mobileappws.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {

    private Date date;
    private String localizedMessage;

    public ErrorMessage(Date date, String localizedMessage) {
        this.date = date;
        this.localizedMessage = localizedMessage;
    }
}
