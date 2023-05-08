package com.week10springsecurity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "comments")
public class Comments {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize =  1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private String Content;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    @Column(name = "is_admin_comment")
    private boolean isAdminComment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Posts post;
}
