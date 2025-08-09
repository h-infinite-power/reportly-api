package com.human.infinite.power.reportly.domain.analysisresult.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;

import java.util.List;

/**
 * 분석결과 생성 요청 DTO
 */
@Getter
public class AnalysisResultCreateRequestDto {
    
    /**
     * 분석 대상 브랜드 번호
     */
    private Long targetCompanyNo;
    
    /**
     * 업종 번호
     */
    private Long industryNo;
    
    /**
     * 경쟁사 브랜드 번호 목록
     */
    private List<Long> competitorCompanyNoList;
}
