package com.playground.twitter.services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "db")
@Data
public class ConfigDB {
    public enum DB_Type {
        FILE,
        HEAP,
        OFF_HEAP
    }

    public String dir;

    public DB_Type type;

}
