package com.cookie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MyBoardListResponseDto {
    private long id;
    private String imgUrl;
    private String title;
    private String description;
    private List<String> memberImgUrlList;
}
