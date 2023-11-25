package com.cookie.controller;

import com.cookie.dto.BoardRequestDto;
import com.cookie.dto.BoardResponseDto;
import com.cookie.dto.CodeReqeustDto;
import com.cookie.dto.MyBoardListResponseDto;
import com.cookie.global.response.DataResponseDto;
import com.cookie.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "쿠키박스 관련 API")
@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "쿠키박스 생성 API")
    @PostMapping
    public DataResponseDto<BoardResponseDto> save(@RequestHeader("Authorization") String authorization, @RequestBody BoardRequestDto boardRequestDto) {
        return DataResponseDto.from(boardService.save(authorization, boardRequestDto));
    }

    @Operation(summary = "내가 만든 쿠키박스 API")
    @GetMapping
    public DataResponseDto<List<MyBoardListResponseDto>> getMyBoardList(@RequestHeader("Authorization") String authorization) {
        return DataResponseDto.from(boardService.getMyBoardList(authorization));
    }

    @Operation(summary = "내가 참여한 쿠키박스 API")
    @GetMapping("/participation")
    public DataResponseDto<List<MyBoardListResponseDto>> getParticipationList(@RequestHeader("Authorization") String authorization) {
        return DataResponseDto.from(boardService.getParticipantBoardList(authorization));
    }

    @GetMapping("/matchingcode/{board_id}")
    public DataResponseDto<Boolean> isCorrectCode(@RequestHeader("Authorization") String authorization, @RequestBody CodeReqeustDto codeReqeustDto, @PathVariable Long board_id) {
        return DataResponseDto.from(boardService.isCorrectCode(authorization, codeReqeustDto, board_id));
    }
}
