package com.cookie.global;

import com.cookie.domain.Member;
import com.cookie.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberInitializer {

    private final MemberRepository memberRepository;

//    @PostConstruct
//    public void init() {
//        memberRepository.save(Member.builder()
//            .token("token")
//            .nickname("초록색빼빼로")
//            .profile("https://dimg.donga.com/wps/NEWS/IMAGE/2023/06/22/119900215.1.jpg")
//            .build()
//        );
//    }
}
