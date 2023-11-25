package com.cookie.service;

import com.cookie.domain.*;
import com.cookie.dto.*;
import com.cookie.global.exception.BusinessException;
import com.cookie.global.response.ErrorCode;
import com.cookie.global.response.ResponseDto;
import com.cookie.repository.BoardRepository;
import com.cookie.repository.ImageRepository;
import com.cookie.repository.MemberRepository;
import com.cookie.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public ResponseEntity savePost(Long board_id, String authorization, PostCreateRequestDto requestDto){
        try {
            Member member = memberRepository.findByToken(authorization)
                    .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
            Board board = boardRepository.findById(board_id)
                    .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

            Post post = Post.builder()
                    .board(board)
                    .member(member)
                    .title(requestDto.getTitle())
                    .content(requestDto.getContent())
                    .build();
            postRepository.save(post);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    //게시글 목록 조회
    @Transactional(readOnly = true)
    public PostListResponseDto findPostList(Long board_id, String authorization){
        Member member = memberRepository.findByToken(authorization)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        List<Post> posts = postRepository.findAllByBoardId(board_id);

        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for(int i=0; i<posts.size(); i++) {
            Post post = posts.get(i);

            Member writer = memberRepository.findById(post.getMember().getId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

            List<Image> imageList = imageRepository.findImageByPost_Id(post.getId());
            List<PostImageDto> postImageDtos = imageList.stream().map(PostImageDto::new).collect(Collectors.toList());

            MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                    .id(writer.getId())
                    .nickname(writer.getNickname())
                    .profile(writer.getProfile())
                    .build();

            PostDetailResponseDto detailResponseDto = PostDetailResponseDto.builder()
                    .id(post.getId())
                    .member(memberResponseDto)
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdDate(post.getCreatedAt().toString())
                    .imageList(postImageDtos)
                    .build();

            PostResponseDto responseDto = PostResponseDto.builder()
                    .id(detailResponseDto.getId())
                    .member(detailResponseDto.getMember())
                    .title(detailResponseDto.getTitle())
                    .content(detailResponseDto.getContent())
                    .createdDate(detailResponseDto.getCreatedDate())
                    .img(detailResponseDto.getImageList().get(0).getImageUrl())
                    .build();

            postResponseDtos.add(responseDto);
        }

            PostListResponseDto listResponseDto = PostListResponseDto.builder()
                    .postList(postResponseDtos)
                    .build();
        return listResponseDto;

    }

    //게시글 1개 조회
    @Transactional
    public PostDetailResponseDto findPostDetail(Long board_id, Long post_id, String authorization){
        Member member = memberRepository.findByToken(authorization)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다: " + post_id));

        Member writer = memberRepository.findById(post.getMember().getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        List<Image> imageList = imageRepository.findImageByPost_Id(post_id);
        List<PostImageDto> postImageDtos = imageList.stream().map(PostImageDto::new).collect(Collectors.toList());

        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .id(writer.getId())
                .nickname(writer.getNickname())
                .profile(writer.getProfile())
                .build();

        PostDetailResponseDto responseDto = PostDetailResponseDto.builder()
                .id(post.getId())
                .member(memberResponseDto)
                .title(post.getTitle())
                .content(post.getContent())
                .createdDate(post.getCreatedAt().toString())
                .imageList(postImageDtos)
                .build();
        return responseDto;
    }

}
