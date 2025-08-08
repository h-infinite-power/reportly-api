package com.human.infinite.power.reportly.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 이름만 받는 공통 요청 DTO
 */
@Getter
@Setter
public class NameRequestDto {
    
    /**
     * 이름
     */
    private String companyName;
    
    /**
     * 기본 생성자
     */
    public NameRequestDto() {
    }
    
    /**
     * 생성자
     * 
     * @param companyName 이름
     */
    public NameRequestDto(String companyName) {
        this.companyName = companyName;
    }
}
