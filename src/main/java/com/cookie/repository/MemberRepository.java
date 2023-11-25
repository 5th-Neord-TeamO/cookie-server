package com.cookie.repository;

import com.cookie.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByToken(String authorization);
}
