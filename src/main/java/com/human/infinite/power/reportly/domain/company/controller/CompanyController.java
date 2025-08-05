package com.human.infinite.power.reportly.domain.company.controller;

import com.human.infinite.power.reportly.domain.company.dto.CompanyResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 회사(브랜드) 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/reportly-api/companies")
public class CompanyController {

    /**
     * 브랜드 목록을 조회합니다.
     * 메인 페이지에서 selectBox 형태로 브랜드 목록을 조회할 때 사용됩니다.
     *
     * @return 브랜드 목록
     */
    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getAllCompanies() {
        // TODO: service 호출
        return ResponseEntity.ok(null);
    }
}
