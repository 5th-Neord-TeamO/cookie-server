package com.cookie.global.response;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Success
    OK("0000", HttpStatus.OK, "OK"),

    // Member
    MEMBER_NOT_FOUND("M000", HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    MEMBER_UNAUTHORIZED("M401", HttpStatus.UNAUTHORIZED, "허가되지 않은 유저입니다."),

    // Post
    POST_NOT_FOUND("P000", HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),

    // Comment
    COMMENT_NOT_FOUND("C000", HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),

    // Server
    INTERNAL_ERROR("S000", HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다."),
    METHOD_NOT_ALLOWED("S001", HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP Method 요청입니다.")
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
            .filter(Predicate.not(String::isBlank))
            .orElse(this.getMessage());
    }
}
