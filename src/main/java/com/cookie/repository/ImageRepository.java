package com.cookie.repository;

import com.cookie.domain.Comment;
import com.cookie.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findImageByPost_Id(Long post_id);

    @Query(nativeQuery = true, value = "select * from image where post_id=:post_id")
    List<Image> findAllByPostId(@Param("post_id") Long postId);
}
