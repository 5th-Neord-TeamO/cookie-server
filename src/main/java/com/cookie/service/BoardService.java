package com.cookie.service;

import com.cookie.domain.Board;
import com.cookie.dto.BoardRequestDto;
import com.cookie.dto.BoardResponseDto;
import com.cookie.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardResponseDto save(String authorization, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.save(
            Board.builder()
                .code("test")
                .title(boardRequestDto.getTitle())
                .description(boardRequestDto.getDescription())
                .imgUrl("test")
                .build()
        );

        return BoardResponseDto.builder()
            .title(board.getTitle())
            .description(board.getDescription())
            .imgUrl(board.getImgUrl())
            .build();
    }
}
