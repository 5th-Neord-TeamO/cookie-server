package com.cookie.repository;

import com.cookie.domain.Board;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b.code FROM Board b")
    List<String> findAllCodes();
}
