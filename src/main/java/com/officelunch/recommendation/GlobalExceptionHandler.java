package com.officelunch.recommendation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecommendationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(RecommendationNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("RECOMMENDATION_NOT_FOUND", exception.getMessage()));
    }

    @ExceptionHandler({InvalidRecommendationStateException.class, RestaurantNotRecommendedException.class})
    public ResponseEntity<ErrorResponse> handleConflict(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("INVALID_RECOMMENDATION_STATE", exception.getMessage()));
    }

    @ExceptionHandler(NoRestaurantCandidateException.class)
    public ResponseEntity<ErrorResponse> handleNoCandidate(NoRestaurantCandidateException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse("NO_RESTAURANT_CANDIDATE", exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("INVALID_REQUEST", "요청값을 확인해주세요."));
    }
}
