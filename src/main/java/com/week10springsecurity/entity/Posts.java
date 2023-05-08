package com.week10springsecurity.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "post")
@Builder
public class Posts {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize =  1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now()   ;
    private LocalDateTime updatedAt;
    private String img_url;
    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id")
    private User user;

    @OneToMany(fetch =  FetchType.LAZY,
    mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column
    @JsonBackReference
    private List<Comments> commentsList = new ArrayList<>();
    @OneToMany(mappedBy = "post")
    private List<Likes> likesList = new ArrayList<>();
}
