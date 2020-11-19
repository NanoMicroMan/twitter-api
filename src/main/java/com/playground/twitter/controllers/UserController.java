package com.playground.twitter.controllers;

import com.playground.twitter.domain.UserService;
import com.playground.twitter.errors.NickNameExistsError;
import com.playground.twitter.errors.UserNotFound;
import com.playground.twitter.models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
public class UserController {
    @Autowired
    UserService userService;
    
    @GetMapping("/users")
    public Collection<User> all(){
        return userService.getAllUsers();
    }

    @GetMapping("/user/{nick}")
    public User one(@PathVariable final String nick) throws UserNotFound {
        return userService.getUserByNick(nick);
    }
    
    @PostMapping("/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User register(@RequestBody final User user) throws NickNameExistsError {
        return userService.registerUser(user);
    }
    
    @PatchMapping("/user/{nick}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public User updateName(@PathVariable final String nick, final String name) throws UserNotFound {
        return userService.updateUserName(nick, name);
    }

    @PutMapping("/user/{nick}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public User addFollow(@PathVariable final String nick, final String follow) throws UserNotFound {
        return userService.addFollow(nick, follow);
    }


    @GetMapping("/user/{nick}/followers")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public Collection<String> followers(@PathVariable final String nick) throws UserNotFound {
        return userService.getFollowers(nick);
    }
}
