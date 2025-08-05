package com.human.infinite.power.reportly.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 전역 예외 처리 핸들러
 * 모든 컨트롤러에서 발생하는 예외를 처리합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * UserException 처리 (400 Bad Request)
     * 사용자의 잘못된 요청에 대한 예외를 처리합니다.
     *
     * @param e UserException
     * @return 400 Bad Request 응답
     */
    @ExceptionHandler(UserException.class)
    public ResponseEntity<Map<String, String>> handleUserException(UserException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * RuntimeException 처리 (500 Internal Server Error)
     * 예상치 못한 서버 에러에 대한 예외를 처리합니다.
     *
     * @param e RuntimeException
     * @return 500 Internal Server Error 응답
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
        // 운영 환경에서는 stackTrace를 노출하지 않도록 처리 필요
        e.printStackTrace(); // 개발 환경에서만 stackTrace 출력
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "예상치 못한 서버 에러입니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Exception 처리 (500 Internal Server Error)
     * 그 외 모든 예외에 대한 처리입니다.
     *
     * @param e Exception
     * @return 500 Internal Server Error 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        // 운영 환경에서는 stackTrace를 노출하지 않도록 처리 필요
        e.printStackTrace(); // 개발 환경에서만 stackTrace 출력
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "예상치 못한 서버 에러입니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
