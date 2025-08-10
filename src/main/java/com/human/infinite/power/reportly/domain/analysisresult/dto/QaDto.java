package com.human.infinite.power.reportly.domain.analysisresult.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 질문/답변 정보 DTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    private String categoryName;
    
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
}
