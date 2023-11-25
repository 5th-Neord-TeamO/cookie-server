package com.cookie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private String nickname;
    private String title;
    private String content;
    private String createdDate;
    private String img;
}
