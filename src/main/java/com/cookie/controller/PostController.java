package com.cookie.controller;

import com.cookie.dto.*;

import com.cookie.global.response.DataResponseDto;
import com.cookie.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "쿠키 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class PostController {

    private final PostService postService;

    //게시글 목록 조회
    @GetMapping("/board/{board_id}")
    public DataResponseDto<List<PostResponseDto>> getPostList(@PathVariable Long board_id, @RequestHeader("Authorization") String authorization){
        return DataResponseDto.from(postService.findPostList(board_id, authorization));
    }

    //게시글 1개 조회
    @GetMapping("/board/{board_id}/post/{post_id}")
    public DataResponseDto<PostDetailResponseDto> getPostDetail(@PathVariable Long board_id, @PathVariable Long post_id, @RequestHeader("Authorization") String authorization){
        return DataResponseDto.from(postService.findPostDetail(board_id, post_id, authorization));
    }

    @PostMapping("/board/{board_id}")
    public DataResponseDto<PostCreateResponseDto> createPost(@PathVariable Long board_id, @RequestHeader("Authorization") String authorization, @RequestBody PostCreateRequestDto requestDto){
        return DataResponseDto.from(postService.savePost(board_id, authorization, requestDto));
    }

    // 게시글 수정
    @PutMapping("/board/{board_id}/post/{post_id}")
    public DataResponseDto<Map<String, Long>> updatePost(@PathVariable Long board_id, @PathVariable Long post_id, @RequestHeader("Authorization") String authorization, @RequestBody PostUpdateRequestDto requestDto){
        return DataResponseDto.from(postService.updatePost(board_id, post_id, authorization, requestDto));
    }

    //게시글 삭제
    @DeleteMapping("/board/{board_id}/post/{post_id}")
    public DataResponseDto<?> deletePost(@RequestHeader("Authorization") String authorization, @PathVariable Long board_id, @PathVariable Long post_id) {
        postService.deletePost(authorization, board_id, post_id);
        return DataResponseDto.from(null);
    }

}
