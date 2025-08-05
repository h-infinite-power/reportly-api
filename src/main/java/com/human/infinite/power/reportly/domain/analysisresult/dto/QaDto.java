package com.human.infinite.power.reportly.domain.analysisresult.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.List;

/**
 * 질문/답변 정보 DTO
 */
public class QaDto {
    
    /**
     * 질문 번호
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long questionNo;
    
    /**
     * 카테고리 번호
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryNo;
    
    /**
     * 질문 내용
     */
    private String question;
    
    /**
     * 타겟 회사 정보
     */
    private CompanyInfoDto targetCompanyInfo;
    
    /**
     * 경쟁사 회사 정보 목록
     */
    private List<CompanyInfoDto> competitorCompanyInfo;
    
    /**
     * 기본 생성자
     */
    public QaDto() {
    }
    
    /**
     * 생성자
     * 
     * @param questionNo 질문 번호
     * @param categoryNo 카테고리 번호
     * @param question 질문 내용
     * @param targetCompanyInfo 타겟 회사 정보
     * @param competitorCompanyInfo 경쟁사 회사 정보 목록
     */
    public QaDto(Long questionNo, Long categoryNo, String question, 
                CompanyInfoDto targetCompanyInfo, List<CompanyInfoDto> competitorCompanyInfo) {
        this.questionNo = questionNo;
        this.categoryNo = categoryNo;
        this.question = question;
        this.targetCompanyInfo = targetCompanyInfo;
        this.competitorCompanyInfo = competitorCompanyInfo;
    }
    
    /**
     * 생성자 (String 타입 번호들)
     * 
     * @param questionNo 질문 번호
     * @param categoryNo 카테고리 번호
     * @param question 질문 내용
     * @param targetCompanyInfo 타겟 회사 정보
     * @param competitorCompanyInfo 경쟁사 회사 정보 목록
     */
    public QaDto(String questionNo, String categoryNo, String question, 
                CompanyInfoDto targetCompanyInfo, List<CompanyInfoDto> competitorCompanyInfo) {
        this.questionNo = questionNo != null ? Long.valueOf(questionNo) : null;
        this.categoryNo = categoryNo != null ? Long.valueOf(categoryNo) : null;
        this.question = question;
        this.targetCompanyInfo = targetCompanyInfo;
        this.competitorCompanyInfo = competitorCompanyInfo;
    }
    
    // Getter and Setter
    public Long getQuestionNo() {
        return questionNo;
    }
    
    public void setQuestionNo(Long questionNo) {
        this.questionNo = questionNo;
    }
    
    /**
     * String 타입으로 질문 번호 설정
     * 
     * @param questionNo 질문 번호 (String)
     */
    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo != null ? Long.valueOf(questionNo) : null;
    }
    
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
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public CompanyInfoDto getTargetCompanyInfo() {
        return targetCompanyInfo;
    }
    
    public void setTargetCompanyInfo(CompanyInfoDto targetCompanyInfo) {
        this.targetCompanyInfo = targetCompanyInfo;
    }
    
    public List<CompanyInfoDto> getCompetitorCompanyInfo() {
        return competitorCompanyInfo;
    }
    
    public void setCompetitorCompanyInfo(List<CompanyInfoDto> competitorCompanyInfo) {
        this.competitorCompanyInfo = competitorCompanyInfo;
    }
}
