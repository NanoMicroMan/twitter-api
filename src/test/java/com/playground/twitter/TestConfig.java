package com.playground.twitter;

import com.playground.twitter.service.IDataStore;
import com.playground.twitter.service.impl.DataStore;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public IDataStore DataStore() {
        return new DataStore();
    }
}
