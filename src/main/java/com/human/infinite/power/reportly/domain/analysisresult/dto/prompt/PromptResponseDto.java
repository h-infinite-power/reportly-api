package com.human.infinite.power.reportly.domain.analysisresult.dto.prompt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AI 프롬프트 응답 DTO
 * 추후 실제 AI 서빙서버와 연동시 사용될 응답 구조
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PromptResponseDto {
    
    /**
     * 브랜드명
     */
    private String brand;
    
    /**
     * 카테고리별 분석 결과 목록
     */
    private List<CategoryResultDto> categoryResults;
    
    /**
     * AI 인사이트 요약
     */
    private InsightSummaryDto insightSummary;
}
