package com.human.infinite.power.reportly.domain.company.controller;

import com.human.infinite.power.reportly.common.dto.KeyValueResponseDto;
import com.human.infinite.power.reportly.domain.company.dto.CompanyCreateRequestDto;
import com.human.infinite.power.reportly.domain.company.dto.CompanyResponseDto;
import com.human.infinite.power.reportly.domain.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.human.infinite.power.reportly.common.exception.UserException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 회사(브랜드) 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/reportly-api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    /**
     * 브랜드를 생성합니다.
     * 메인 페이지에서 selectBox 목록에 해당하는 부분을 저장하는데 사용됩니다.
     *
     * @param requestDto 이름 요청 DTO
     * @return 생성된 브랜드 정보
     */
    @PostMapping
    public ResponseEntity<KeyValueResponseDto> createCompany(@RequestBody CompanyCreateRequestDto requestDto) {
        if (requestDto == null || requestDto.getCompanyName() == null || requestDto.getCompanyName().trim().isEmpty()) {
            throw new UserException("브랜드 이름은 필수입니다.");
        }
        KeyValueResponseDto response = companyService.createCompany(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 브랜드 목록을 조회합니다.
     * 메인 페이지에서 selectBox 형태로 브랜드 목록을 조회할 때 사용됩니다.
     *
     * @return 브랜드 목록
     */
    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getAllCompanies() {
        List<CompanyResponseDto> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }
}
