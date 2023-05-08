package com.week10springsecurity.service.serviceImpl;



import com.week10springsecurity.dto.CommentsDTO;
import com.week10springsecurity.entity.Comments;
import com.week10springsecurity.entity.Posts;
import com.week10springsecurity.entity.User;
import com.week10springsecurity.enums.Role;
import com.week10springsecurity.exception.BlogApiException;
import com.week10springsecurity.exception.ResourceNotFoundException;
import com.week10springsecurity.repository.CommentRepository;
import com.week10springsecurity.repository.PostRepository;
import com.week10springsecurity.repository.UserRepository;
import com.week10springsecurity.service.CommentsService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentsService {
    private final CommentRepository commentRepository;
    private  final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentsDTO createComment(long postId, CommentsDTO commentsDTO, HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Posts post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "id", postId));
        Comments comments = mapToEntity(commentsDTO);

        comments.setPost(post);
        comments.setUser(user);

        if (user.getRole().equals(Role.ADMIN)) {
            comments.setAdminComment(true);
        } else {
            comments.setAdminComment(false);
        }

        Comments savedComment = commentRepository.save(comments);
        return mapToDTO(savedComment);
    }

    @Override
    public List<CommentsDTO> getCommentByPostId(long postId) {
        //retrieve comments By postId;
        List<Comments>  comments = commentRepository.findByPostId(postId);
        //convert list of comment entities to list od comment  dto's
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentsDTO getCommendById(long postId, long commentId) {
        Posts post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "id", postId));

        Comments  comments = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comments", "id", commentId));
        if(!comments.getPost().getId().equals(post.getId())){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST,"Comments does not belong to a post");
        }
        return mapToDTO(comments);
    }

    @Override
    public CommentsDTO getCommentByUserId(long userId, long commentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Comments userComments = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if(!userComments.getUser().getId().equals(user.getId())){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to user");

        }
        return mapToDTO(userComments);
    }

    @Override
    public CommentsDTO updateComment(Long postId, Long commentId, CommentsDTO commentsReq) {
        Posts posts = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comments comments = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comments", "id", commentId));
        if(!comments.getPost().getId().equals(posts.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not Belong to Post");
        }
        comments.setId(commentsReq.getId());
        comments.setContent(commentsReq.getContent());
        Comments updatedComment = commentRepository.save(comments);
        return mapToDTO(updatedComment);
    }

    @Override
    public CommentsDTO updateCommentByUserId(Long commentId, CommentsDTO commentsReq, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Comments comments = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));
        if(!comments.getUser().getId().equals(user.getId())){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST," Comment does not Belong to User ");
        }
//        comments.setId(commentsReq.getId());
        comments.setContent(commentsReq.getContent());
        Comments updateUserComment = commentRepository.save(comments);
        return mapToDTO(updateUserComment);
    }


    @Override
    public void userDeleteComment(Long userId, Long commentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id",userId));
        Comments comments = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));
        if(!comments.getUser().getId().equals(user.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment not found");

        }
        commentRepository.delete(comments);
    }


    private CommentsDTO mapToDTO(Comments comments){
        CommentsDTO commentsDTO = new CommentsDTO();
//        commentsDTO.setId(comments.getId());
        commentsDTO.setContent(comments.getContent());
        return  commentsDTO;
    }

    private Comments  mapToEntity(CommentsDTO commentsDTO){
        Comments comments = new Comments();
//        comments.setId(commentsDTO.getId());
        comments.setContent(commentsDTO.getContent());
        return comments;
    }
}
