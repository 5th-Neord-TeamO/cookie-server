package com.cookie.controller;

import com.cookie.dto.PostCreateRequestDto;

import com.cookie.dto.PostDetailResponseDto;
import com.cookie.dto.PostListResponseDto;
import com.cookie.global.response.DataResponseDto;
import com.cookie.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class PostController {

    private final PostService postService;

    //게시글 목록 조회
    @GetMapping("/board/{board_id}")
    public DataResponseDto<PostListResponseDto> getPostList(@PathVariable Long board_id, @RequestHeader("Authorization") String authorization){
        return DataResponseDto.from(postService.findPostList(board_id, authorization));
    }

    //게시글 1개 조회
    @GetMapping("/board/{board_id}/post/{post_id}")
    public DataResponseDto<PostDetailResponseDto> getPostDetail(@PathVariable Long board_id, @PathVariable Long post_id, @RequestHeader("Authorization") String authorization){
        return DataResponseDto.from(postService.findPostDetail(board_id, post_id, authorization));
    }

    @PostMapping("/board/{board_id}")
    public DataResponseDto<ResponseEntity> createPost(@PathVariable Long board_id, @RequestHeader("Authorization") String authorization, @RequestBody PostCreateRequestDto requestDto){
        return DataResponseDto.from(postService.savePost(board_id, authorization, requestDto));
    }

}
