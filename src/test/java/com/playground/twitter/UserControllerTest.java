package com.playground.twitter;

import com.playground.twitter.controllers.UserController;
import com.playground.twitter.errors.NickNameExistsError;
import com.playground.twitter.models.User;
import com.playground.twitter.services.IDataStore;
import com.playground.twitter.services.impl.MapdbDataStore;
import com.playground.twitter.domain.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserService userService = Mockito.mock(UserService.class);

    @InjectMocks
    private IDataStore dataStore = Mockito.mock(MapdbDataStore.class);

    //Controller that is being tested.
    @Autowired
    @InjectMocks
    private UserController userController;

    @SneakyThrows
    @Test
    public void registerOK() {
        final User user = new User("New", "user Name");
        given(userController.register(user)).willReturn(user);
        when(userController.register(user)).thenThrow(NickNameExistsError.class);
    }
}
