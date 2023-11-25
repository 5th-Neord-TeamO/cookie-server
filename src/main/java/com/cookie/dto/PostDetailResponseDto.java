package com.cookie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostDetailResponseDto {
    private Long id;
    private MemberResponseDto member;
    private String title;
    private String content;
    private String createdDate;
    private List<PostImageDto> imgUrlList;
}
