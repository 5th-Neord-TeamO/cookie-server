package com.cookie.controller;

import com.cookie.dto.BoardRequestDto;
import com.cookie.dto.BoardResponseDto;
import com.cookie.dto.MyBoardListResponseDto;
import com.cookie.global.response.DataResponseDto;
import com.cookie.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public DataResponseDto<List<MyBoardListResponseDto>> getMyBoardList(@RequestHeader("Authorization") String authorization) {
        return DataResponseDto.from(boardService.getMyBoardList(authorization));
    }
}
