package com.cookie.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardRequestDto {

    private String title;
    private String description;
    private String imgFile;
}
