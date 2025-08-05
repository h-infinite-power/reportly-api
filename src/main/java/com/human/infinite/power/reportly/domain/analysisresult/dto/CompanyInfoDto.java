package com.human.infinite.power.reportly.domain.analysisresult.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.List;

/**
 * 회사 정보 DTO
 */
public class CompanyInfoDto {
    
    /**
     * 회사 번호
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long companyNo;
    
    /**
     * 회사 이름
     */
    private String companyName;
    
    /**
     * 요약
     */
    private String summary;
    
    /**
     * 상세 내용 (targetCompanyInfo에만 존재)
     */
    private String content;
    
    /**
     * 긍정 키워드 목록 (targetCompanyInfo에만 존재)
     */
    private List<String> positiveKeyword;
    
    /**
     * 부정 키워드 목록 (targetCompanyInfo에만 존재)
     */
    private List<String> negativeKeyword;
    
    /**
     * 회사 카테고리 점수
     */
    private Double companyCategoryScore;
    
    /**
     * 기본 생성자
     */
    public CompanyInfoDto() {
    }
    
    /**
     * 생성자 (경쟁사용 - content, keyword 제외)
     * 
     * @param companyNo 회사 번호
     * @param companyName 회사 이름
     * @param summary 요약
     * @param companyCategoryScore 회사 카테고리 점수
     */
    public CompanyInfoDto(Long companyNo, String companyName, String summary, Double companyCategoryScore) {
        this.companyNo = companyNo;
        this.companyName = companyName;
        this.summary = summary;
        this.companyCategoryScore = companyCategoryScore;
    }
    
    /**
     * 생성자 (타겟회사용 - 모든 필드 포함)
     * 
     * @param companyNo 회사 번호
     * @param companyName 회사 이름
     * @param summary 요약
     * @param content 상세 내용
     * @param positiveKeyword 긍정 키워드 목록
     * @param negativeKeyword 부정 키워드 목록
     * @param companyCategoryScore 회사 카테고리 점수
     */
    public CompanyInfoDto(Long companyNo, String companyName, String summary, String content,
                         List<String> positiveKeyword, List<String> negativeKeyword, Double companyCategoryScore) {
        this.companyNo = companyNo;
        this.companyName = companyName;
        this.summary = summary;
        this.content = content;
        this.positiveKeyword = positiveKeyword;
        this.negativeKeyword = negativeKeyword;
        this.companyCategoryScore = companyCategoryScore;
    }
    
    // Getter and Setter
    public Long getCompanyNo() {
        return companyNo;
    }
    
    public void setCompanyNo(Long companyNo) {
        this.companyNo = companyNo;
    }
    
    /**
     * String 타입으로 회사 번호 설정
     * 
     * @param companyNo 회사 번호 (String)
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo != null ? Long.valueOf(companyNo) : null;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public List<String> getPositiveKeyword() {
        return positiveKeyword;
    }
    
    public void setPositiveKeyword(List<String> positiveKeyword) {
        this.positiveKeyword = positiveKeyword;
    }
    
    public List<String> getNegativeKeyword() {
        return negativeKeyword;
    }
    
    public void setNegativeKeyword(List<String> negativeKeyword) {
        this.negativeKeyword = negativeKeyword;
    }
    
    public Double getCompanyCategoryScore() {
        return companyCategoryScore;
    }
    
    public void setCompanyCategoryScore(Double companyCategoryScore) {
        this.companyCategoryScore = companyCategoryScore;
    }
}
