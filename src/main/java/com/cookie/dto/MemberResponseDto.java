package com.cookie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MemberResponseDto {
    private Long id;
    private String nickname;
    private String profile;
}
