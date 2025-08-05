package com.human.infinite.power.reportly.domain.analysisresult.dto;

import java.util.List;

/**
 * 분석결과 상세 응답 DTO
 */
public class AnalysisResultDetailResponseDto {
    
    /**
     * 강점
     */
    private String strongPoint;
    
    /**
     * 약점
     */
    private String weakPoint;
    
    /**
     * 개선제안
     */
    private String improvements;
    
    /**
     * 질문/답변 목록
     */
    private List<QaDto> qaList;
    
    /**
     * 기본 생성자
     */
    public AnalysisResultDetailResponseDto() {
    }
    
    /**
     * 생성자
     * 
     * @param strongPoint 강점
     * @param weakPoint 약점
     * @param improvements 개선제안
     * @param qaList 질문/답변 목록
     */
    public AnalysisResultDetailResponseDto(String strongPoint, String weakPoint, String improvements, List<QaDto> qaList) {
        this.strongPoint = strongPoint;
        this.weakPoint = weakPoint;
        this.improvements = improvements;
        this.qaList = qaList;
    }
    
    // Getter and Setter
    public String getStrongPoint() {
        return strongPoint;
    }
    
    public void setStrongPoint(String strongPoint) {
        this.strongPoint = strongPoint;
    }
    
    public String getWeakPoint() {
        return weakPoint;
    }
    
    public void setWeakPoint(String weakPoint) {
        this.weakPoint = weakPoint;
    }
    
    public String getImprovements() {
        return improvements;
    }
    
    public void setImprovements(String improvements) {
        this.improvements = improvements;
    }
    
    public List<QaDto> getQaList() {
        return qaList;
    }
    
    public void setQaList(List<QaDto> qaList) {
        this.qaList = qaList;
    }
}
