package com.week10springsecurity.service;


import com.week10springsecurity.dto.LikeDTO;
import jakarta.servlet.http.HttpSession;

public interface LikesService {
    LikeDTO createLike(LikeDTO likeDTO, Long postId, HttpSession session);
}
