package com.playground.twitter.service;

import com.playground.twitter.errors.NickNameExistsError;
import com.playground.twitter.errors.UserNotFound;
import com.playground.twitter.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

public interface IUserService {
    @GetMapping("/users")
    Collection<User> all();

    @GetMapping("/user/{nick}")
    User one(@PathVariable String nick);

    @PostMapping("/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    User register(User user) throws NickNameExistsError;

    @PatchMapping("/user")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    User updateName(String nickName, String name) throws UserNotFound;

    @PutMapping("/user")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    User addFollow(String nickFollower, String nickFollow) throws UserNotFound;
}
