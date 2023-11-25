package com.cookie.controller;

import com.cookie.dto.BoardRequestDto;
import com.cookie.dto.BoardResponseDto;
import com.cookie.global.response.DataResponseDto;
import com.cookie.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public DataResponseDto<BoardResponseDto> save(
        @RequestHeader("Authorization") String authorization,
        @RequestBody BoardRequestDto boardRequestDto) {
        return DataResponseDto.from(boardService.save(authorization, boardRequestDto));
    }
}
