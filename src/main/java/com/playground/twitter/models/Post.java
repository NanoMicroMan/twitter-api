package com.playground.twitter.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
public class Post implements Serializable, Comparable<Post> {
    private String text;
    private LocalDateTime date;

    public Post(String text) {
        this.text = text;
        date = LocalDateTime.now();
    }

    @Override
    public int compareTo(@NotNull Post o) {
        return this.getDate().compareTo(o.getDate());
    }
}
