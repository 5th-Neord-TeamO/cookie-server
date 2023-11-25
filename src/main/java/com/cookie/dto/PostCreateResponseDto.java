package com.cookie.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostCreateResponseDto {

        private String createdAt;
        private String title;
        private String description;
        private AuthorResponseDto member;

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class AuthorResponseDto {
            private Long id;
            private String nickname;
            private String imgUrl;
        }
}
