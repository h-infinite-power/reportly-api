package com.human.infinite.power.reportly.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * key-value 형태의 공통 응답 DTO
 * jobNo와 같은 Long 타입 숫자를 String으로 변환하여 반환할 때 사용
 */
@Getter
@AllArgsConstructor
public class NoResponseDto {
    
    /**
     * 키 (예: "jobNo")
     */
    private String key;
    
    /**
     * 값 (Long 타입이지만 JSON 응답시 String으로 변환)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long no;
    
    /**
     * NoResponseDto 생성 팩토리 메서드
     * 
     * @param key 키 값
     * @param no 번호 값
     * @return NoResponseDto 인스턴스
     */
    public static NoResponseDto of(String key, Long no) {
        return new NoResponseDto(key, no);
    }
}
