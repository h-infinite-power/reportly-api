package com.human.infinite.power.reportly.domain.job.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 분석결과 정보 응답 DTO
 * 카테고리별 점수의 select box를 위한 회사명, analysisResultNo 목록 조회용
 */
public class AnalysisResultInfoResponseDto {
    
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
     * 분석결과 번호
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long analysisResultNo;
    
    /**
     * 타겟 회사 여부 (Y: 타겟 회사, N: 경쟁 회사)
     */
    private String targetCompanyYn;
    
    /**
     * 기본 생성자
     */
    public AnalysisResultInfoResponseDto() {
    }
    
    /**
     * 생성자
     * 
     * @param companyNo 회사 번호
     * @param companyName 회사 이름
     * @param analysisResultNo 분석결과 번호
     * @param targetCompanyYn 타겟 회사 여부
     */
    public AnalysisResultInfoResponseDto(Long companyNo, String companyName, Long analysisResultNo, String targetCompanyYn) {
        this.companyNo = companyNo;
        this.companyName = companyName;
        this.analysisResultNo = analysisResultNo;
        this.targetCompanyYn = targetCompanyYn;
    }
    
    // Getter and Setter
    public Long getCompanyNo() {
        return companyNo;
    }
    
    public void setCompanyNo(Long companyNo) {
        this.companyNo = companyNo;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public Long getAnalysisResultNo() {
        return analysisResultNo;
    }
    
    public void setAnalysisResultNo(Long analysisResultNo) {
        this.analysisResultNo = analysisResultNo;
    }
    
    public String getTargetCompanyYn() {
        return targetCompanyYn;
    }
    
    public void setTargetCompanyYn(String targetCompanyYn) {
        this.targetCompanyYn = targetCompanyYn;
    }
}
