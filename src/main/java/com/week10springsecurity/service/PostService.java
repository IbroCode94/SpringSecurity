package com.week10springsecurity.service;




import com.week10springsecurity.dto.PostDTO;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO, HttpSession session);

  List<PostDTO> getAllPost();

  PostDTO getPostById(long id);

  PostDTO updatePost(PostDTO postDTO, long id, long userId);
  void deletedPostByID(long id);
}
