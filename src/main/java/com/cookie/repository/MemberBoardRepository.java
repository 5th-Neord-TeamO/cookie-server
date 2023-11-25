package com.cookie.repository;

import com.cookie.domain.MemberBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBoardRepository extends JpaRepository<MemberBoard, Long> {

}
