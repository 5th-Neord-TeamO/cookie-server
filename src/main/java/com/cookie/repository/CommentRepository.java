package com.cookie.repository;

import com.cookie.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(nativeQuery = true, value = "select * from comment where post_id=:post_id")
    List<Comment> findAllByPostId(@Param("post_id") Long postId);
}
