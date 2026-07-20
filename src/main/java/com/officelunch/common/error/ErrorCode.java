package com.officelunch.common.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "요청값을 확인해주세요."),
    RECOMMENDATION_CANDIDATE_NOT_FOUND(HttpStatus.NOT_FOUND, "추천할 식당이 없습니다."),
    RECOMMENDATION_SESSION_NOT_FOUND(HttpStatus.NOT_FOUND, "추천 세션을 찾을 수 없습니다."),
    RECOMMENDATION_EXHAUSTED(HttpStatus.CONFLICT, "더 이상 추천할 식당이 없습니다."),
    RECOMMENDATION_ALREADY_SELECTED(HttpStatus.CONFLICT, "이미 식당을 선택한 추천 세션입니다."),
    RESTAURANT_NOT_RECOMMENDED(HttpStatus.BAD_REQUEST, "추천받은 식당만 선택할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
