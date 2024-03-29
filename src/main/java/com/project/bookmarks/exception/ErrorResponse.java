package com.project.bookmarks.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class ErrorResponse {
    private Date timestamp;
    private String message;
    private Object details;

    public ErrorResponse(Date timestamp, String message, Object details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        log.error(message);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }
}