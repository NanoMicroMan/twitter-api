package com.playground.twitter.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFound extends Exception {
    public UserNotFound() {
        super("User not found");
    }
}
