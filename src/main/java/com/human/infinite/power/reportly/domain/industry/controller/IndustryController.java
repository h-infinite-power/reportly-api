package com.human.infinite.power.reportly.domain.industry.controller;

import com.human.infinite.power.reportly.domain.industry.dto.IndustryResponseDto;
import com.human.infinite.power.reportly.domain.industry.service.IndustryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 업종 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/reportly-api/industries")
@RequiredArgsConstructor
public class IndustryController {

    private final IndustryService industryService;

    /**
     * 업종 목록을 조회합니다.
     * 메인 페이지에서 selectBox 형태로 업종 목록을 조회할 때 사용됩니다.
     *
     * @return 업종 목록
     */
    @GetMapping
    public ResponseEntity<List<IndustryResponseDto>> getAllIndustries() {
        List<IndustryResponseDto> industries = industryService.getAllIndustries();
        return ResponseEntity.ok(industries);
    }
}
