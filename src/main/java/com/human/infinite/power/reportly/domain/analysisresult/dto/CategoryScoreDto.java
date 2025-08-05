package com.human.infinite.power.reportly.domain.analysisresult.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 카테고리별 점수 DTO (공통 사용)
 */
public class CategoryScoreDto {
    
    /**
     * 카테고리 번호
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryNo;
    
    /**
     * 카테고리 이름
     */
    private String categoryName;
    
    /**
     * 회사 점수
     */
    private Double companyScore;
    
    /**
     * 기본 생성자
     */
    public CategoryScoreDto() {
    }
    
    /**
     * 생성자
     * 
     * @param categoryNo 카테고리 번호
     * @param categoryName 카테고리 이름
     * @param companyScore 회사 점수
     */
    public CategoryScoreDto(Long categoryNo, String categoryName, Double companyScore) {
        this.categoryNo = categoryNo;
        this.categoryName = categoryName;
        this.companyScore = companyScore;
    }
    
    /**
     * 생성자 (String 타입 카테고리 번호)
     * 
     * @param categoryNo 카테고리 번호
     * @param categoryName 카테고리 이름
     * @param companyScore 회사 점수
     */
    public CategoryScoreDto(String categoryNo, String categoryName, Double companyScore) {
        this.categoryNo = categoryNo != null ? Long.valueOf(categoryNo) : null;
        this.categoryName = categoryName;
        this.companyScore = companyScore;
    }
    
    // Getter and Setter
    public Long getCategoryNo() {
        return categoryNo;
    }
    
    public void setCategoryNo(Long categoryNo) {
        this.categoryNo = categoryNo;
    }
    
    /**
     * String 타입으로 카테고리 번호 설정
     * 
     * @param categoryNo 카테고리 번호 (String)
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo != null ? Long.valueOf(categoryNo) : null;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public Double getCompanyScore() {
        return companyScore;
    }
    
    public void setCompanyScore(Double companyScore) {
        this.companyScore = companyScore;
    }
}
