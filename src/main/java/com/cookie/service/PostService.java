package com.cookie.service;

import com.cookie.domain.*;
import com.cookie.dto.*;
import com.cookie.global.exception.BusinessException;
import com.cookie.global.response.ErrorCode;
import com.cookie.repository.*;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;

    private final CommentService commentService;

    @Transactional
    public PostCreateResponseDto savePost(Long board_id, String authorization, PostCreateRequestDto requestDto){
            Member member = memberRepository.findByToken(authorization)
                    .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
            Board board = boardRepository.findById(board_id)
                    .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

            Post post = Post.builder()
                .board(board)
                .member(member)
                .title(requestDto.getTitle())
                .content(requestDto.getDescription())
                .build();

            imageRepository.save(Image.builder()
                    .imgUrl(requestDto.getImgUrl())
                    .post(post)
                    .build()
            );

            postRepository.save(post);

        return PostCreateResponseDto.builder()
            .id(board.getId())
            .build();

    }

    //게시글 목록 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> findPostList(Long board_id, String authorization){
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
                    .imgUrl(writer.getProfile())
                    .build();

            PostDetailResponseDto detailResponseDto = PostDetailResponseDto.builder()
                    .id(post.getId())
                    .member(memberResponseDto)
                    .title(post.getTitle())
                    .description(post.getContent())
                    .createdDate(post.getCreatedAt().withNano(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                    .imgUrlList(postImageDtos)
                    .build();

            if (detailResponseDto.getImgUrlList().size() == 0){
                PostResponseDto responseDto = PostResponseDto.builder()
                        .id(detailResponseDto.getId())
                        .member(detailResponseDto.getMember())
                        .title(detailResponseDto.getTitle())
                        .description(detailResponseDto.getDescription())
                        .createdDate(detailResponseDto.getCreatedDate())
                        .build();
                postResponseDtos.add(responseDto);
            }

            else {
                PostResponseDto responseDto = PostResponseDto.builder()
                        .id(detailResponseDto.getId())
                        .member(detailResponseDto.getMember())
                        .title(detailResponseDto.getTitle())
                        .description(detailResponseDto.getDescription())
                        .createdDate(detailResponseDto.getCreatedDate())
                        .imgUrl(detailResponseDto.getImgUrlList().get(0).getImgUrl())
                        .build();
                postResponseDtos.add(responseDto);
            }
        }

        postResponseDtos.sort(Comparator.comparing(PostResponseDto::getCreatedDate).reversed());

        return postResponseDtos.stream().toList();

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
                .imgUrl(writer.getProfile())
                .build();

        PostDetailResponseDto responseDto = PostDetailResponseDto.builder()
                .id(post.getId())
                .member(memberResponseDto)
                .title(post.getTitle())
                .description(post.getContent())
                .createdDate(post.getCreatedAt().withNano(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                .imgUrlList(postImageDtos)
                .build();
        return responseDto;
    }

    public Map<String, Long> updatePost(Long boardId, Long postId, String authorization, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        if (imageRepository.findImageByPost_Id(postId).isEmpty()){
            imageRepository.save(Image.builder()
                    .imgUrl(requestDto.getImgUrl())
                    .post(post)
                    .build()
            );
        }
        Image image = imageRepository.findImageByPost_Id(postId).get(0);
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getDescription());
        image.setImgUrl(requestDto.getImgUrl());
        return Map.of("id", 1L);
    }


    //게시글 삭제
    public void deletePost(String authorization, Long boardId, Long postId) {
        Member member = memberRepository.findByToken(authorization)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = commentService.validateExistPost(postId);
        List<Comment> comments= commentRepository.findAllByPostId(postId);
        List<Image> images = imageRepository.findAllByPostId(postId);

        if (commentService.validateMember(member.getId(), post.getMember().getId())) {
            for (int i=0; i<comments.size(); i++) {
                commentRepository.delete(comments.get(i));
            }
            for (int i=0; i<images.size(); i++) {
                imageRepository.delete(images.get(i));
            }
            postRepository.delete(post);
        } else {
            throw new BusinessException(ErrorCode.MEMBER_UNAUTHORIZED);
        }
    }
}
