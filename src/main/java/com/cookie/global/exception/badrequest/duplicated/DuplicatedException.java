package com.cookie.global.exception.badrequest.duplicated;

import com.cookie.global.exception.BusinessException;
import com.cookie.global.response.ErrorCode;

public class DuplicatedException extends BusinessException {

    public DuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
