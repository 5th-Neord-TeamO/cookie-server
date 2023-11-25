package com.cookie.service;

import com.cookie.domain.Comment;
import com.cookie.domain.Member;
import com.cookie.domain.Post;
import com.cookie.dto.CommentRequestDto;
import com.cookie.global.exception.BusinessException;
import com.cookie.global.response.ErrorCode;
import com.cookie.repository.CommentRepository;
import com.cookie.repository.MemberRepository;
import com.cookie.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void save(String authorization, CommentRequestDto commentRequestDto, Long postId) {
        Member member = memberRepository.findByToken(authorization)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = validateExistPost(postId);

        commentRepository.save(Comment.builder()
                .comment(commentRequestDto.getDescription())
                .post(post)
                .member(member)
                .build()
        );

    }

    // 해당 게시글 존재 여부 확인
    public Post validateExistPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
    }

}
