package com.cookie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MyBoardListResponseDto {
    private String thumbnail;
    private String title;
    private List<String> memberList;
}
