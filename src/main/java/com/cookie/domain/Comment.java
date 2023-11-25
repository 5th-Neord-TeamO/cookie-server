package com.cookie.domain;

import com.cookie.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String comment;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Comment(String comment, Member member, Post post){
        this.comment = comment;
        this.member = member;
        this.post = post;
    }

    public void update(String comment){
        this.comment = comment;
    }
}
