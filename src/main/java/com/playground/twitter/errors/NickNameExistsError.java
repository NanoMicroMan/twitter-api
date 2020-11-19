package com.playground.twitter.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class NickNameExistsError extends Exception {
    public NickNameExistsError() {
        super("NickName already exists, it must be unique");
    }
}

