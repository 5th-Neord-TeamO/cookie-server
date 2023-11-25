package com.cookie.controller;

import com.cookie.dto.CommentRequestDto;
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
    public DataResponseDto<?> getParticipationList(@RequestHeader("Authorization") String authorization, @RequestBody CommentRequestDto commentRequestDto, @PathVariable Long post_id) {
        commentService.save(authorization, commentRequestDto, post_id);
        return DataResponseDto.from(null);
    }
}
