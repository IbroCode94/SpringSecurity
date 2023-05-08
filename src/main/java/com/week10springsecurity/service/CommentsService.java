package com.week10springsecurity.service;


import com.week10springsecurity.dto.CommentsDTO;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface CommentsService {
    CommentsDTO createComment(long postId, CommentsDTO commentsDTO, HttpSession httpSession);
    List<CommentsDTO> getCommentByPostId(long postId);
    CommentsDTO getCommendById(long postId, long commentId);
    CommentsDTO getCommentByUserId(long userId, long commentId);

    CommentsDTO updateComment(Long postId, Long commentId, CommentsDTO commentsReq);
     CommentsDTO updateCommentByUserId(Long commentId, CommentsDTO commentsReq, HttpSession session);
      void userDeleteComment(Long userId, Long commentId);
}
