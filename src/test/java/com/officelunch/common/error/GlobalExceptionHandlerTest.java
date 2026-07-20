package com.officelunch.common.error;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    @Test
    void 비즈니스_예외를_표준_오류_응답으로_변환한다() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        BusinessException exception = new BusinessException(
            ErrorCode.RECOMMENDATION_SESSION_NOT_FOUND
        );

        ResponseEntity<ErrorResponse> response = handler.handleBusinessException(exception);

        assertEquals(404, response.getStatusCode().value());
        assertEquals(
            "RECOMMENDATION_SESSION_NOT_FOUND",
            response.getBody().getCode()
        );
        assertEquals(
            "추천 세션을 찾을 수 없습니다.",
            response.getBody().getMessage()
        );
    }
}
