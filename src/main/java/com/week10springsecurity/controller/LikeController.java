package com.week10springsecurity.controller;


import com.week10springsecurity.dto.LikeDTO;
import com.week10springsecurity.service.LikesService;
import com.week10springsecurity.service.PostService;
import com.week10springsecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
public class LikeController {

   private final LikesService likesService;
   private final UserService userService;
   private  final PostService postService;

    public LikeController(LikesService likesService, UserService userService, PostService postService) {
        this.likesService = likesService;
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("new/{id}")
    public ResponseEntity<LikeDTO> createLikes(LikeDTO likeDTO, @PathVariable(value = "id") Long postId , HttpServletRequest req){
return new ResponseEntity<>(likesService.createLike(likeDTO, postId, req.getSession()), HttpStatus.CREATED);
    }
}
