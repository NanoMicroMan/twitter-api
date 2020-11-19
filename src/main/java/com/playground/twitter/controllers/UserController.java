package com.playground.twitter.controllers;

import com.playground.twitter.errors.NickNameExistsError;
import com.playground.twitter.errors.UserNotFound;
import com.playground.twitter.models.User;
import com.playground.twitter.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
public class UserController {
    @Autowired
    IUserService userService;
    
    @GetMapping("/users")
    public Collection<User> all(){
        return userService.getAllUsers();
    }

    @GetMapping("/user/{nick}")
    public User one(@PathVariable final String nick) {
        return userService.getUserByNick(nick);
    }
    
    @PostMapping("/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User register(final User user) throws NickNameExistsError {
        return userService.registerUser(user);
    }
    
    @PatchMapping("/user")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public User updateName(final String nickName, final String name) throws UserNotFound {
        return userService.updateUserName(nickName, name);
    }

    @PutMapping("/user")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public User addFollow(final String nickFollower, final String nickFollow) throws UserNotFound {
        return userService.addFollow(nickFollower, nickFollow);
    }
}
