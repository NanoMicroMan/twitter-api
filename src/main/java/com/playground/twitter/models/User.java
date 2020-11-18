package com.playground.twitter.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    String nickName;
    String name;
    private final Set<String> follows = new HashSet<>();

    public void addFollow(final String nickFollow) {
        follows.add(nickFollow);
    }
}
