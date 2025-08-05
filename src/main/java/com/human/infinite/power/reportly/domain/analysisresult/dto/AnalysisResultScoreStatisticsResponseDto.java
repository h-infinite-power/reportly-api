package com.human.infinite.power.reportly.domain.analysisresult.dto;

import java.util.List;

/**
 * 분석결과 점수 통계 응답 DTO
 */
public class AnalysisResultScoreStatisticsResponseDto {
    
    /**
     * 타겟 회사의 카테고리별 점수 목록
     * competitorCategoryAvgScoreList와의 계산을 통해 '강점 카테고리', '약점 카테고리'에 사용
     */
    private List<CategoryScoreDto> targetCompanyCategoryScoreList;
    
    /**
     * 경쟁사들의 카테고리별 평균 점수 목록
     * 경쟁사 비교 그래프에서 사용
     */
    private List<CategoryScoreDto> competitorCategoryAvgScoreList;
    
    /**
     * 기본 생성자
     */
    public AnalysisResultScoreStatisticsResponseDto() {
    }
    
    /**
     * 생성자
     * 
     * @param targetCompanyCategoryScoreList 타겟 회사의 카테고리별 점수 목록
     * @param competitorCategoryAvgScoreList 경쟁사들의 카테고리별 평균 점수 목록
     */
    public AnalysisResultScoreStatisticsResponseDto(List<CategoryScoreDto> targetCompanyCategoryScoreList,
                                                   List<CategoryScoreDto> competitorCategoryAvgScoreList) {
        this.targetCompanyCategoryScoreList = targetCompanyCategoryScoreList;
        this.competitorCategoryAvgScoreList = competitorCategoryAvgScoreList;
    }
    
    // Getter and Setter
    public List<CategoryScoreDto> getTargetCompanyCategoryScoreList() {
        return targetCompanyCategoryScoreList;
    }
    
    public void setTargetCompanyCategoryScoreList(List<CategoryScoreDto> targetCompanyCategoryScoreList) {
        this.targetCompanyCategoryScoreList = targetCompanyCategoryScoreList;
    }
    
    public List<CategoryScoreDto> getCompetitorCategoryAvgScoreList() {
        return competitorCategoryAvgScoreList;
    }
    
    public void setCompetitorCategoryAvgScoreList(List<CategoryScoreDto> competitorCategoryAvgScoreList) {
        this.competitorCategoryAvgScoreList = competitorCategoryAvgScoreList;
    }
}
