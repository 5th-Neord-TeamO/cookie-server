package com.cookie.dto;

import lombok.Getter;

@Getter
public class PostCreateRequestDto {
    private String title;
    private String imgUrl;
    private String description;
}

// 글만 올리는 경우
//{
//    “title”: “dd”,
//    “imgUrl”: null,
//    “description”: “dd”
//}

// 이미지만 올리는 경우
//{
//    “title”: null,
//    “imgUrl”: "dd",
//    “description”: null
//}
