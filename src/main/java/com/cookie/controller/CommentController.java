package com.cookie.controller;

import com.cookie.dto.CommentRequestDto;
import com.cookie.dto.CommentResponseDto;
import com.cookie.global.response.DataResponseDto;
import com.cookie.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{post_id}")
    public DataResponseDto<CommentResponseDto> save(@RequestHeader("Authorization") String authorization, @RequestBody CommentRequestDto commentRequestDto, @PathVariable Long post_id) {
        CommentResponseDto responseDto = commentService.save(authorization, commentRequestDto, post_id);
        return DataResponseDto.from(responseDto);
    }

    @PatchMapping("/{comment_id}")
    public DataResponseDto<?> patchComment(@RequestHeader("Authorization") String authorization, @RequestBody CommentRequestDto commentRequestDto, @PathVariable Long comment_id) {
        commentService.updateComment(authorization, commentRequestDto, comment_id);
        return DataResponseDto.from(null);
    }
}