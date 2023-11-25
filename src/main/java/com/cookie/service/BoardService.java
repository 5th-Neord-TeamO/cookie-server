package com.cookie.service;

import com.cookie.domain.Board;
import com.cookie.domain.Member;
import com.cookie.domain.MemberBoard;
import com.cookie.dto.BoardRequestDto;
import com.cookie.dto.BoardResponseDto;
import com.cookie.dto.MyBoardListResponseDto;
import com.cookie.global.exception.BusinessException;
import com.cookie.global.response.ErrorCode;
import com.cookie.repository.BoardRepository;
import com.cookie.repository.MemberBoardRepository;
import com.cookie.repository.MemberRepository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final MemberBoardRepository memberBoardRepository;

    public BoardResponseDto save(String authorization, BoardRequestDto boardRequestDto) {
        Member member = memberRepository.findByToken(authorization)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        // 이제까지 생성된 방 코드
        List<String> generatedCodes = boardRepository.findAllCodes();
        log.info("generatedCodes: {}", generatedCodes);

        // 방 코드 생성
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder(4);
        SecureRandom random = new SecureRandom();

        while (true) {
            for (int i = 0; i < 4; i++) {
                int randomIndex = random.nextInt(characters.length());
                randomString.append(characters.charAt(randomIndex));
            }

            // 이전에 생성되지 않은 방 코드라면 while문 탈출
            if (!generatedCodes.contains(randomString.toString())) {
                break;
            } else {
                log.info("중복된 방 코드가 만들어져 코드 생성을 재시도합니다", randomString.toString());
                // 중복이 있으면 다시 시도
                randomString.setLength(0);
            }
        }

        Board board = boardRepository.save(
                Board.builder()
                        .code(randomString.toString())
                        .title(boardRequestDto.getTitle())
                        .description(boardRequestDto.getDescription())
                        .imgUrl(boardRequestDto.getImgUrl())
                        .build()
        );

        memberBoardRepository.save(
                MemberBoard.builder()
                        .member(member)
                        .board(board)
                        .isLeader(true)
                        .build()
        );

        return BoardResponseDto.builder()
                .code(randomString.toString())
                .build();
    }

    public List<MyBoardListResponseDto> getMyBoardList(String authorization) {
        Member member = memberRepository.findByToken(authorization)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        List<MemberBoard> findMemberBoard = memberBoardRepository.findAllByMemberId(member.getId());
        List<Member> findAllMember = memberRepository.findAll();

        List<MyBoardListResponseDto> response = new ArrayList<>();
        List<String> memberList = new ArrayList<>();

        for(Member members : findAllMember) {
            memberList.add(members.getProfile());
        }

        for (MemberBoard memberBoard : findMemberBoard) {
            if (memberBoard.getIsLeader()) {
                Board board = memberBoard.getBoard();
                MyBoardListResponseDto dto = MyBoardListResponseDto.builder()
                        .memberList(memberList)
                        .thumbnail(board.getImgUrl())
                        .title(board.getTitle())
                        .build();
                response.add(dto);
            }
        }

        return response;
    }
}
