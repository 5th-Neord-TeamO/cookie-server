package com.cookie.member.dao;

import com.cookie.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByUserId(String userId);

}
