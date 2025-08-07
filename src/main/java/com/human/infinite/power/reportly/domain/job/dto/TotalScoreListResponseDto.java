package com.human.infinite.power.reportly.domain.job.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 종합 점수 목록 응답 DTO
 * 경쟁사와 우리 회사의 절대 점수를 비교한 데이터를 반환합니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TotalScoreListResponseDto {
    
    /**
     * 타겟회사 순위
     */
    private Integer targetRank;
    
    /**
     * 타겟회사 기준 회사 번호
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long targetCompanyNo;
    
    /**
     * 타겟회사 총점
     */
    private Double targetTotalScore;
    
    /**
     * 타겟회사 기준 평균점수 (타겟 회사는 제외)
     */
    private Double competitorAvgTotalScore;
    
    /**
     * 경쟁력 순위에서의 총 회사 수
     */
    private Integer totalCompanyCount;
}
