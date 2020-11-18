package com.playground.twitter.services;

import com.playground.twitter.errors.NickNameExistsError;
import com.playground.twitter.errors.UserNotFound;
import com.playground.twitter.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

public interface IUserService {

    Collection<User> all();

    User one(@PathVariable String nick);

    User register(User user) throws NickNameExistsError;

    User updateName(String nickName, String name) throws UserNotFound;

    User addFollow(String nickFollower, String nickFollow) throws UserNotFound;

    Collection<String> getFollowers(String nickName);
}
