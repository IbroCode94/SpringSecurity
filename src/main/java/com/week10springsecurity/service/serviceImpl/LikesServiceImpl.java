package com.week10springsecurity.service.serviceImpl;



import com.week10springsecurity.dto.LikeDTO;
import com.week10springsecurity.entity.Likes;
import com.week10springsecurity.entity.Posts;
import com.week10springsecurity.entity.User;
import com.week10springsecurity.enums.Role;
import com.week10springsecurity.exception.ResourceNotFoundException;
import com.week10springsecurity.repository.LikeRepository;
import com.week10springsecurity.repository.PostRepository;
import com.week10springsecurity.repository.UserRepository;
import com.week10springsecurity.service.LikesService;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikesServiceImpl implements LikesService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private ModelMapper modelMapper;

    public LikesServiceImpl(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {

        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper  = modelMapper;
    }

    @Override
    public LikeDTO createLike(LikeDTO likeDTO, Long postId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Posts posts = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "id", postId));

        // Check if like exists and delete it
        Optional<Likes> existingLike = likeRepository.findBuyer(user.getId(), posts.getId());
        existingLike.ifPresent(likeRepository::delete);

        if (user.getRole().equals(Role.USER)) {
            Likes likes = new Likes();
            likes.setLikes(true);
            likes.setUser(user);
            likes.setPost(posts) ;
            Likes newLike = likeRepository.save(likes);

            return modelMapper.map(newLike, LikeDTO.class);
        } else {
            return null;
        }
    }

}
