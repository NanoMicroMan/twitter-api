package com.playground.twitter.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashSet;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class User implements Serializable, Comparable<User> {
    String nickName;
    String name;
    private final HashSet<String> follows = new HashSet<>();

    public void setNickName(String nickName) {
        this.nickName = nickName==null? null: nickName.trim();
    }

    public void addFollow(final String nickFollow) {
        follows.add(nickFollow);
    }

    @Override
    public int compareTo(@NotNull User o) {
        int res = this.getNickName().compareTo(o.getNickName());
        if (res==0) res = this.getName().compareTo(o.getName());
        if (res==0) res = this.getFollows().size() - o.getFollows().size();
        if (res==0) res = this.getFollows().containsAll(o.getFollows())? 0: -1;
        return res;
    }
}
