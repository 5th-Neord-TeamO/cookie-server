package com.cookie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostDetailResponseDto {
    private Long id;
    private String nickname;
    private String title;
    private String content;
    private String createdDate;
    private String img;
    private List<PostImageDto> imageList;
}
