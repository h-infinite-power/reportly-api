package com.human.infinite.power.reportly.domain.analysisresult.dto.prompt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 카테고리별 분석 결과 DTO
 * AI 프롬프트 응답의 category_results 부분에 해당
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResultDto {
    
    /**
     * 질문 ID
     */
    private Long questionId;
    
    /**
     * 카테고리명
     */
    private String category;

    /**
     * 답변 요약 내용
     */
    private String summary;

    /**
     * 답변 내용
     */
    private String answer;
    
    /**
     * 긍정 키워드 목록
     */
    private List<String> positiveKeyword;
    
    /**
     * 부정 키워드 목록
     */
    private List<String> negativeKeyword;
    
    /**
     * 점수 (0-100)
     */
    private Integer score;
    
    /**
     * 신뢰도 (0.0-1.0)
     */
    private Double confidence;
    
    /**
     * 근거 자료
     */
    private String supportingEvidence;
}
