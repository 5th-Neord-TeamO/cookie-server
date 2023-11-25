package com.cookie.service;

import com.cookie.domain.Board;
import com.cookie.domain.Member;
import com.cookie.domain.MemberBoard;
import com.cookie.dto.BoardRequestDto;
import com.cookie.dto.BoardResponseDto;
import com.cookie.dto.CodeReqeustDto;
import com.cookie.dto.MyBoardListResponseDto;
import com.cookie.dto.PostResponseDto;
import com.cookie.global.exception.BusinessException;
import com.cookie.global.response.ErrorCode;
import com.cookie.repository.BoardRepository;
import com.cookie.repository.MemberBoardRepository;
import com.cookie.repository.MemberRepository;

import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                .boardId(board.getId())
                .build();
    }

    public List<MyBoardListResponseDto> getMyBoardList(String authorization) {
        Member member = memberRepository.findByToken(authorization)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        List<MemberBoard> findMemberBoard = memberBoardRepository.findAllByMemberId(member.getId());
        List<Member> findAllMember = memberRepository.findAll();

        List<MyBoardListResponseDto> response = new ArrayList<>();
        List<String> memberList = new ArrayList<>();

        for (Member members : findAllMember) {
            memberList.add(members.getProfile());
        }

        for (MemberBoard memberBoard : findMemberBoard) {
            if (memberBoard.getIsLeader()) {
                Board board = memberBoard.getBoard();
                MyBoardListResponseDto dto = MyBoardListResponseDto.builder()
                        .id(board.getId())
                        .memberImgUrlList(memberList)
                        .imgUrl(board.getImgUrl())
                        .title(board.getTitle())
                        .description(board.getDescription())
                        .build();
                response.add(dto);
            }
        }

        response.sort(Comparator.comparing(MyBoardListResponseDto::getId).reversed());

        return response;
    }

    public List<MyBoardListResponseDto> getParticipantBoardList(String authorization) {
        Member member = memberRepository.findByToken(authorization)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        List<MemberBoard> memberBoardList = memberBoardRepository.findAllByMemberId(member.getId());
        List<Member> findAllMemberList = memberRepository.findAll();

        List<MyBoardListResponseDto> response = new ArrayList<>();
        List<String> memberImgUrlList = new ArrayList<>();

        // 해당 게시판에 가입된 사람들 프로필 추가
        for (Member members : findAllMemberList) {
            for (MemberBoard allMemberBoard : memberBoardList) {
                if (!members.getId().equals(allMemberBoard.getMember().getId())) {
                    memberImgUrlList.add(members.getProfile());
                    break;
                }
            }
        }

        for (MemberBoard memberBoard : memberBoardList) {
            // 내 id랑 게시판의 회원 id가 같을 때
            if (memberBoard.getMember().getId().equals(member.getId())) {
                Board board = memberBoard.getBoard();
                MyBoardListResponseDto dto = MyBoardListResponseDto.builder()
                        .id(board.getId())
                        .imgUrl(board.getImgUrl())
                        .memberImgUrlList(memberImgUrlList)
                        .title(board.getTitle())
                        .description(board.getDescription())
                        .build();
                response.add(dto);
            }
        }

        response.sort(Comparator.comparing(MyBoardListResponseDto::getId).reversed());

        return response;

    }

    public Boolean isCorrectCode(String authorization, CodeReqeustDto codeRequestDto, Long boardId) {
        memberRepository.findByToken(authorization)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        String requestCode = codeRequestDto.getCode();

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isEmpty()) {
            throw new BusinessException(ErrorCode.POST_NOT_FOUND);
        }

        Board board = optionalBoard.get();
        String boardCode = board.getCode();

        return requestCode.equals(boardCode);

    }
}
