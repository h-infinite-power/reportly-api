package com.human.infinite.power.reportly.common.exception;

/**
 * 사용자 정의 예외 클래스
 * 400 Bad Request 에러를 처리하기 위한 예외입니다.
 */
public class UserException extends RuntimeException {
    
    /**
     * 기본 생성자
     */
    public UserException() {
        super();
    }
    
    /**
     * 메시지를 포함한 생성자
     * 
     * @param message 예외 메시지
     */
    public UserException(String message) {
        super(message);
    }
    
    /**
     * 메시지와 원인을 포함한 생성자
     * 
     * @param message 예외 메시지
     * @param cause 원인 예외
     */
    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * 원인만 포함한 생성자
     * 
     * @param cause 원인 예외
     */
    public UserException(Throwable cause) {
        super(cause);
    }
}
