package com.cookie.dto;

import com.cookie.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostImageDto {
    private String imgUrl;

    public PostImageDto(Image image){
        this.imgUrl = image.getImgUrl();
    }
}
