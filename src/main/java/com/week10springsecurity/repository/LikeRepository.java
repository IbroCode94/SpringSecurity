package com.week10springsecurity.repository;


import com.week10springsecurity.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository  extends JpaRepository<Likes, Long> {
@Query(value ="SELECT *  FROM likes WHERE user_id =?1  AND post_id=?2 ", nativeQuery = true)
    Optional<Likes> findBuyer(Long user, Long posts);
}
