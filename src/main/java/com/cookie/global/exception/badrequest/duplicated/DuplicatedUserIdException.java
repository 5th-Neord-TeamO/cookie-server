package com.cookie.global.exception.badrequest.duplicated;

import com.cookie.global.response.ErrorCode;

public class DuplicatedUserIdException extends DuplicatedException {

    public DuplicatedUserIdException() {
        super(ErrorCode.DUPLICATED_USER_ID);
    }

}
