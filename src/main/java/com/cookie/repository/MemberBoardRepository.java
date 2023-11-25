package com.cookie.repository;

import com.cookie.domain.MemberBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberBoardRepository extends JpaRepository<MemberBoard, Long> {

    List<MemberBoard> findAllByMemberId(Long id);
}
