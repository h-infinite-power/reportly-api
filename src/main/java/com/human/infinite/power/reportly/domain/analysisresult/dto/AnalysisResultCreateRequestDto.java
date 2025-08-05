package com.human.infinite.power.reportly.domain.analysisresult.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.List;

/**
 * 분석결과 생성 요청 DTO
 */
public class AnalysisResultCreateRequestDto {
    
    /**
     * 분석 대상 브랜드 번호
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long targetCompanyNo;
    
    /**
     * 업종 번호
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long industryNo;
    
    /**
     * 경쟁사 브랜드 번호 목록
     */
    private List<Long> competitorCompanyNoList;
    
    /**
     * 기본 생성자
     */
    public AnalysisResultCreateRequestDto() {
    }
    
    /**
     * 생성자
     * 
     * @param targetCompanyNo 분석 대상 브랜드 번호
     * @param industryNo 업종 번호
     * @param competitorCompanyNoList 경쟁사 브랜드 번호 목록
     */
    public AnalysisResultCreateRequestDto(Long targetCompanyNo, Long industryNo, List<Long> competitorCompanyNoList) {
        this.targetCompanyNo = targetCompanyNo;
        this.industryNo = industryNo;
        this.competitorCompanyNoList = competitorCompanyNoList;
    }
    
    /**
     * 생성자 (String 타입 번호들)
     * 
     * @param targetCompanyNo 분석 대상 브랜드 번호
     * @param industryNo 업종 번호
     * @param competitorCompanyNoList 경쟁사 브랜드 번호 목록
     */
    public AnalysisResultCreateRequestDto(String targetCompanyNo, String industryNo, List<String> competitorCompanyNoList) {
        this.targetCompanyNo = targetCompanyNo != null ? Long.valueOf(targetCompanyNo) : null;
        this.industryNo = industryNo != null ? Long.valueOf(industryNo) : null;
        this.competitorCompanyNoList = competitorCompanyNoList != null ? 
            competitorCompanyNoList.stream().map(Long::valueOf).toList() : null;
    }
    
    // Getter and Setter
    public Long getTargetCompanyNo() {
        return targetCompanyNo;
    }
    
    public void setTargetCompanyNo(Long targetCompanyNo) {
        this.targetCompanyNo = targetCompanyNo;
    }
    
    /**
     * String 타입으로 분석 대상 브랜드 번호 설정
     * 
     * @param targetCompanyNo 분석 대상 브랜드 번호 (String)
     */
    public void setTargetCompanyNo(String targetCompanyNo) {
        this.targetCompanyNo = targetCompanyNo != null ? Long.valueOf(targetCompanyNo) : null;
    }
    
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
    
    public List<Long> getCompetitorCompanyNoList() {
        return competitorCompanyNoList;
    }
    
    public void setCompetitorCompanyNoList(List<Long> competitorCompanyNoList) {
        this.competitorCompanyNoList = competitorCompanyNoList;
    }
    
    /**
     * String 리스트로 경쟁사 브랜드 번호 목록 설정
     * 
     * @param competitorCompanyNoList 경쟁사 브랜드 번호 목록 (String List)
     */
    public void setCompetitorCompanyNoListFromString(List<String> competitorCompanyNoList) {
        this.competitorCompanyNoList = competitorCompanyNoList != null ? 
            competitorCompanyNoList.stream().map(Long::valueOf).toList() : null;
    }
}
