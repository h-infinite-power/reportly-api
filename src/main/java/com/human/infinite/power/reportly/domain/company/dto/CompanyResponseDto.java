package com.human.infinite.power.reportly.domain.company.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 회사 응답 DTO
 */
public class CompanyResponseDto {
    
    /**
     * 회사 번호
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long companyNo;
    
    /**
     * 회사 이름
     */
    private String companyName;
    
    /**
     * 기본 생성자
     */
    public CompanyResponseDto() {
    }
    
    /**
     * 생성자
     * 
     * @param companyNo 회사 번호
     * @param companyName 회사 이름
     */
    public CompanyResponseDto(Long companyNo, String companyName) {
        this.companyNo = companyNo;
        this.companyName = companyName;
    }
    
    /**
     * 생성자 (String 타입 회사 번호)
     * 
     * @param companyNoStr 회사 번호
     * @param companyName 회사 이름
     */
    public CompanyResponseDto(String companyNoStr, String companyName) {
        this.companyNo = companyNoStr != null ? Long.valueOf(companyNoStr) : null;
        this.companyName = companyName;
    }
    
    // Getter and Setter
    public Long getCompanyNo() {
        return companyNo;
    }
    
    public void setCompanyNo(Long companyNo) {
        this.companyNo = companyNo;
    }
    
    /**
     * String 타입으로 회사 번호 설정
     * 
     * @param companyNoStr 회사 번호 (String)
     */
    public void setCompanyNoFromString(String companyNoStr) {
        this.companyNo = companyNoStr != null ? Long.valueOf(companyNoStr) : null;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
