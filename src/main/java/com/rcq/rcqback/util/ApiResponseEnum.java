package com.rcq.rcqback.util;

import org.springframework.http.HttpStatus;

public enum ApiResponseEnum {
    OK(200 , HttpStatus.OK),
    FAIL(400,HttpStatus.BAD_REQUEST),
    NOT_FOUND(404,HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR(500,HttpStatus.INTERNAL_SERVER_ERROR);


    private final int code;
    private final HttpStatus httpStatus;
    ApiResponseEnum(int code,HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus=httpStatus;
    }

    public int getCode() {
        return code;
    }
    public HttpStatus getHttpStatus(){
        return httpStatus;
    }

}
