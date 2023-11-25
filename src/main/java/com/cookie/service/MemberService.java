package com.cookie.service;

import com.cookie.domain.Member;
import com.cookie.dto.MemberEditRequestDto;
import com.cookie.dto.MemberResponseDto;
import com.cookie.global.exception.BusinessException;
import com.cookie.global.response.ErrorCode;
import com.cookie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto editNickname(String authorization, MemberEditRequestDto requestDto) {
        Member member = memberRepository.findByToken(authorization)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        member.setNickname(requestDto.getNickname());

        MemberResponseDto responseDto = MemberResponseDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .imgUrl(member.getProfile())
                .build();

        return responseDto;
    }
}
