package com.cookie.service;

import com.cookie.domain.Comment;
import com.cookie.domain.Member;
import com.cookie.domain.Post;
import com.cookie.dto.CommentRequestDto;
import com.cookie.dto.CommentResponseDto;
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

    public CommentResponseDto saveComment(String authorization, CommentRequestDto commentRequestDto, Long postId) {
        Member member = memberRepository.findByToken(authorization)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = validateExistPost(postId);

        Comment comment = commentRepository.save(Comment.builder()
                .comment(commentRequestDto.getDescription())
                .post(post)
                .member(member)
                .build()
        );

        return CommentResponseDto.builder().id(comment.getId()).build();

    }

    public void updateComment(String authorization, CommentRequestDto commentRequestDto, Long commentId) {
        Member member = memberRepository.findByToken(authorization)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Comment comment = validateExistComment(commentId);
        if (validateMember(member.getId(), comment.getMember().getId())) {
            comment.update(commentRequestDto.getDescription());
            commentRepository.save(comment);
        } else {
            throw new BusinessException(ErrorCode.MEMBER_UNAUTHORIZED);
        }
    }

    public void deleteComment(String authorization, Long commentId) {
        Member member = memberRepository.findByToken(authorization)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Comment comment = validateExistComment(commentId);
        if (validateMember(member.getId(), comment.getMember().getId())) {
            commentRepository.delete(comment);
        } else {
            throw new BusinessException(ErrorCode.MEMBER_UNAUTHORIZED);
        }

    }

    // 해당 게시글 존재 여부 확인
    public Post validateExistPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
    }

    // 댓글 존재 여부 확인
    public Comment validateExistComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));
    }

    // 내가 쓴 댓글인지 아닌지
    public boolean validateMember(Long memberId, Long postMemberId) {
        return memberId.equals(postMemberId);
    }

}
