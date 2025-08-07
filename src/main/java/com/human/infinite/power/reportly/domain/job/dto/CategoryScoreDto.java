package com.human.infinite.power.reportly.domain.job.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리별 점수 DTO
 * 카테고리 정보와 해당 카테고리의 점수를 포함합니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
     * 카테고리 점수
     */
    private Double categoryScore;
}
