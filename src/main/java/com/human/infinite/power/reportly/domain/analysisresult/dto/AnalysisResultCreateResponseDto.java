package com.human.infinite.power.reportly.domain.analysisresult.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 분석결과 생성 응답 DTO
 */
public class AnalysisResultCreateResponseDto {
    
    /**
     * 분석결과 번호
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long analysisResultNo;
    
    /**
     * 기본 생성자
     */
    public AnalysisResultCreateResponseDto() {
    }
    
    /**
     * 생성자
     * 
     * @param analysisResultNo 분석결과 번호
     */
    public AnalysisResultCreateResponseDto(Long analysisResultNo) {
        this.analysisResultNo = analysisResultNo;
    }
    
    /**
     * 생성자 (String 타입)
     * 
     * @param analysisResultNo 분석결과 번호
     */
    public AnalysisResultCreateResponseDto(String analysisResultNo) {
        this.analysisResultNo = analysisResultNo != null ? Long.valueOf(analysisResultNo) : null;
    }
    
    // Getter and Setter
    public Long getAnalysisResultNo() {
        return analysisResultNo;
    }
    
    public void setAnalysisResultNo(Long analysisResultNo) {
        this.analysisResultNo = analysisResultNo;
    }
    
    /**
     * String 타입으로 분석결과 번호 설정
     * 
     * @param analysisResultNo 분석결과 번호 (String)
     */
    public void setAnalysisResultNo(String analysisResultNo) {
        this.analysisResultNo = analysisResultNo != null ? Long.valueOf(analysisResultNo) : null;
    }
}
