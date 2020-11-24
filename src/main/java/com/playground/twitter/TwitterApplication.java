package com.playground.twitter;

import com.playground.twitter.services.ConfigDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class TwitterApplication {

	public static void main(final String[] args) {
		SpringApplication.run(TwitterApplication.class, args);
	}

	@Configuration
	@EnableConfigurationProperties(ConfigDB.class)
	@EnableScheduling
	public static class AppConfig {
		public static ConfigDB configDB;

		public AppConfig(ConfigDB configDB) {
			this.configDB = configDB;
		}
	}
}
