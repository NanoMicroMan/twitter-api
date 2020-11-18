package com.playground.twitter;

import com.playground.twitter.services.IDataStore;
import com.playground.twitter.services.IUserService;
import com.playground.twitter.services.impl.DataStore;
import com.playground.twitter.services.impl.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public IDataStore DataStore() {
        return new DataStore();
    }

    @Bean
    public IUserService UserService() {
        return new UserService();
    }
}
