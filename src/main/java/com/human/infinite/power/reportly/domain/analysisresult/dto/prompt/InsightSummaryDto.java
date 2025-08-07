package com.human.infinite.power.reportly.domain.analysisresult.dto.prompt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * AI 인사이트 요약 DTO
 * AI 프롬프트 응답의 insight_summary 부분에 해당
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsightSummaryDto {
    
    /**
     * 강점
     */
    private String strengths;
    
    /**
     * 약점
     */
    private String weaknesses;
    
    /**
     * 개선 제안
     */
    private String recommendations;
}
