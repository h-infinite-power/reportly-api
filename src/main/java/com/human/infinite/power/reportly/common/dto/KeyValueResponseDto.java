package com.human.infinite.power.reportly.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 키-값 형태의 공통 응답 DTO
 */
public class KeyValueResponseDto {
    
    /**
     * 키 이름
     */
    private String key;
    
    /**
     * 값 (번호)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long no;
    
    /**
     * 기본 생성자
     */
    public KeyValueResponseDto() {
    }
    
    /**
     * 생성자
     * 
     * @param key 키 이름
     * @param no 값 (번호)
     */
    public KeyValueResponseDto(String key, Long no) {
        this.key = key;
        this.no = no;
    }
    
    // Getter and Setter
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public Long getNo() {
        return no;
    }
    
    public void setNo(Long no) {
        this.no = no;
    }
}
