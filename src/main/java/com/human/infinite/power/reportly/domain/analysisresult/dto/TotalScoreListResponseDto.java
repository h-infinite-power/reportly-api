package com.human.infinite.power.reportly.domain.analysisresult.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 종합 점수 목록 응답 DTO
 */
public class TotalScoreListResponseDto {
    
    /**
     * 타겟회사 순위
     */
    private Integer targetRank;
    
    /**
     * 타겟회사 기준 회사 번호
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long targetCompanyNo;
    
    /**
     * 타겟회사 총점
     */
    private Double targetTotalScore;
    
    /**
     * 타겟회사 기준 평균점수 (타겟 회사는 제외)
     */
    private Double competitorAvgTotalScore;
    
    /**
     * 경쟁력 순위에서의 총 회사 수
     */
    private Integer totalCompanyCount;
    
    /**
     * 기본 생성자
     */
    public TotalScoreListResponseDto() {
    }
    
    /**
     * 생성자
     * 
     * @param targetRank 타겟회사 순위
     * @param targetCompanyNo 타겟회사 기준 회사 번호
     * @param targetTotalScore 타겟회사 총점
     * @param competitorAvgTotalScore 경쟁사 평균점수
     * @param totalCompanyCount 총 회사 수
     */
    public TotalScoreListResponseDto(Integer targetRank, Long targetCompanyNo, Double targetTotalScore, 
                                   Double competitorAvgTotalScore, Integer totalCompanyCount) {
        this.targetRank = targetRank;
        this.targetCompanyNo = targetCompanyNo;
        this.targetTotalScore = targetTotalScore;
        this.competitorAvgTotalScore = competitorAvgTotalScore;
        this.totalCompanyCount = totalCompanyCount;
    }
    
    // Getter and Setter
    public Integer getTargetRank() {
        return targetRank;
    }
    
    public void setTargetRank(Integer targetRank) {
        this.targetRank = targetRank;
    }
    
    public Long getTargetCompanyNo() {
        return targetCompanyNo;
    }
    
    public void setTargetCompanyNo(Long targetCompanyNo) {
        this.targetCompanyNo = targetCompanyNo;
    }
    
    /**
     * String 타입으로 타겟 회사 번호 설정
     * 
     * @param targetCompanyNo 타겟 회사 번호 (String)
     */
    public void setTargetCompanyNo(String targetCompanyNo) {
        this.targetCompanyNo = targetCompanyNo != null ? Long.valueOf(targetCompanyNo) : null;
    }
    
    public Double getTargetTotalScore() {
        return targetTotalScore;
    }
    
    public void setTargetTotalScore(Double targetTotalScore) {
        this.targetTotalScore = targetTotalScore;
    }
    
    public Double getCompetitorAvgTotalScore() {
        return competitorAvgTotalScore;
    }
    
    public void setCompetitorAvgTotalScore(Double competitorAvgTotalScore) {
        this.competitorAvgTotalScore = competitorAvgTotalScore;
    }
    
    public Integer getTotalCompanyCount() {
        return totalCompanyCount;
    }
    
    public void setTotalCompanyCount(Integer totalCompanyCount) {
        this.totalCompanyCount = totalCompanyCount;
    }
}
