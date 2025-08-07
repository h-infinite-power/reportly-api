package com.human.infinite.power.reportly.domain.job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 분석결과 점수 통계 응답 DTO
 * 경쟁사 및 타겟 회사의 카테고리별 평균 점수 통계 데이터를 반환합니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
