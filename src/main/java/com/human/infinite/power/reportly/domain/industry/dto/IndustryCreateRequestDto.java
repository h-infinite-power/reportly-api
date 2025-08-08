package com.human.infinite.power.reportly.domain.industry.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 업종 생성 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class IndustryCreateRequestDto {

    /**
     * 업종 이름
     */
    private String industryName;
}


