package com.cookie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private MemberResponseDto member;
    private String title;
    private String description;
    private String createdDate;
    private String imgUrl;
}
