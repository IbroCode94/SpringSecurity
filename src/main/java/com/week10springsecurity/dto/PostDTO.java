package com.week10springsecurity.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PostDTO {
    private String title;
    private String category;
    private String content;
    private String img_url;
}
