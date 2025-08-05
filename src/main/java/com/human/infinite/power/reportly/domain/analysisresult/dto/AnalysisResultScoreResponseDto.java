package com.human.infinite.power.reportly.domain.analysisresult.dto;

/**
 * 분석결과 점수 응답 DTO
 * CategoryScoreDto와 동일한 구조이므로 상속으로 처리
 */
public class AnalysisResultScoreResponseDto extends CategoryScoreDto {
    
    /**
     * 기본 생성자
     */
    public AnalysisResultScoreResponseDto() {
        super();
    }
    
    /**
     * 생성자
     * 
     * @param categoryNo 카테고리 번호
     * @param categoryName 카테고리 이름
     * @param companyScore 회사 점수
     */
    public AnalysisResultScoreResponseDto(Long categoryNo, String categoryName, Double companyScore) {
        super(categoryNo, categoryName, companyScore);
    }
    
    /**
     * 생성자 (String 타입 카테고리 번호)
     * 
     * @param categoryNo 카테고리 번호
     * @param categoryName 카테고리 이름
     * @param companyScore 회사 점수
     */
    public AnalysisResultScoreResponseDto(String categoryNo, String categoryName, Double companyScore) {
        super(categoryNo, categoryName, companyScore);
    }
}
