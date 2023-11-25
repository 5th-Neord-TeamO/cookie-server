package com.cookie.domain;

import com.cookie.global.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private String nickname;
    private String profile;

    @Builder
    public Member(String token, String nickname, String profile) {
        this.token = token;
        this.nickname = nickname;
        this.profile = profile;
    }
}
