package com.human.infinite.power.reportly.domain.company.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 브랜드 생성 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class CompanyCreateRequestDto {

    /**
     * 브랜드 이름
     */
    private String companyName;
}


