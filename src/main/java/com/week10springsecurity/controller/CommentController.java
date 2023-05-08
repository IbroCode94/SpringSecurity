package com.week10springsecurity.controller;

import com.week10springsecurity.dto.CommentsDTO;
import com.week10springsecurity.service.CommentsService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/comments")
public class CommentController {
    private final CommentsService commentsService;

    public CommentController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping("/new/{postId}")
    public ResponseEntity<CommentsDTO> createComment(@PathVariable(value = "postId") long postId, @RequestBody CommentsDTO commentsDTO, HttpSession session) {
        // method implementation
        return  new ResponseEntity<>(commentsService.createComment(postId,commentsDTO, session), HttpStatus.CREATED);
    }
    @GetMapping("/{postId}/comments")
    public List<CommentsDTO> getCommentByPostId(@PathVariable(value = "postId") long postId){
        return  commentsService.getCommentByPostId(postId);
    }
    @GetMapping("/{postId}/{commentId}/get-comment")
    public ResponseEntity<CommentsDTO> getCommentById(@PathVariable(value = "postId") long postId, @PathVariable(value = "commentId") long commentId){
        CommentsDTO commentsDTO = commentsService.getCommendById(postId, commentId);
        return  new ResponseEntity<>(commentsDTO, HttpStatus.OK);
    }
    @GetMapping("/{userId}/comment/{commentId}")
    public ResponseEntity<CommentsDTO> getCommentByUserId(@PathVariable(value = "userId") long userId, @PathVariable(value = "commentId") long commentId){
        CommentsDTO commentsDTO = commentsService.getCommentByUserId(userId,commentId);
        return  new ResponseEntity<>(commentsDTO, HttpStatus.OK);
    }
    @PutMapping("/update/comment/{id}")
    public  ResponseEntity<CommentsDTO> updateComment(@PathVariable(value = "id") long commentId, @RequestBody CommentsDTO commentsDTO, HttpSession session){
        CommentsDTO updatedComment = commentsService.updateCommentByUserId(commentId,commentsDTO,session );
        return  new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{userId}/comment/{commentId}")
    public ResponseEntity<String> deleteUserComment(@PathVariable(value = "userId") long userId,@PathVariable(value = "commentId") long commentId){
        commentsService.userDeleteComment(userId,commentId);
        return new ResponseEntity<>("Comment Delete", HttpStatus.OK);
    }

}
