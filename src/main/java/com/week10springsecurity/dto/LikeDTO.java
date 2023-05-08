package com.week10springsecurity.dto;


import com.week10springsecurity.entity.Posts;
import com.week10springsecurity.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LikeDTO {
    private boolean like;
    private User user;
    private Posts post;
}
