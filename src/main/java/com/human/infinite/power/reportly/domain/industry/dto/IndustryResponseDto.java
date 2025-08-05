package com.human.infinite.power.reportly.domain.industry.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 업종 응답 DTO
 */
public class IndustryResponseDto {
    
    /**
     * 업종 번호
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long industryNo;
    
    /**
     * 업종 이름
     */
    private String industryName;
    
    /**
     * 기본 생성자
     */
    public IndustryResponseDto() {
    }
    
    /**
     * 생성자
     * 
     * @param industryNo 업종 번호
     * @param industryName 업종 이름
     */
    public IndustryResponseDto(Long industryNo, String industryName) {
        this.industryNo = industryNo;
        this.industryName = industryName;
    }
    
    /**
     * 생성자 (String 타입 업종 번호)
     * 
     * @param industryNo 업종 번호
     * @param industryName 업종 이름
     */
    public IndustryResponseDto(String industryNo, String industryName) {
        this.industryNo = industryNo != null ? Long.valueOf(industryNo) : null;
        this.industryName = industryName;
    }
    
    // Getter and Setter
    public Long getIndustryNo() {
        return industryNo;
    }
    
    public void setIndustryNo(Long industryNo) {
        this.industryNo = industryNo;
    }
    
    /**
     * String 타입으로 업종 번호 설정
     * 
     * @param industryNo 업종 번호 (String)
     */
    public void setIndustryNo(String industryNo) {
        this.industryNo = industryNo != null ? Long.valueOf(industryNo) : null;
    }
    
    public String getIndustryName() {
        return industryName;
    }
    
    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
}
