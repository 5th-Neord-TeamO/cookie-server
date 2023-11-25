package com.cookie.controller;

import com.cookie.dto.CommentRequestDto;
import com.cookie.dto.MemberEditRequestDto;
import com.cookie.dto.MemberResponseDto;
import com.cookie.global.response.DataResponseDto;
import com.cookie.service.MemberService;
import com.cookie.service.S3UploaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/member")
    public DataResponseDto<MemberResponseDto> editNickname(@RequestHeader("Authorization") String authorization, @RequestBody MemberEditRequestDto requestDto) {
        return DataResponseDto.from(memberService.editNickname(authorization, requestDto));
    }
}
