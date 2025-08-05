package com.human.infinite.power.reportly.domain.company.service;

import com.human.infinite.power.reportly.domain.company.dto.CompanyResponseDto;
import com.human.infinite.power.reportly.domain.company.entity.Company;
import com.human.infinite.power.reportly.domain.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 회사(브랜드) 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;

    /**
     * 모든 회사 목록을 조회합니다.
     * 메인 페이지에서 selectBox 형태로 브랜드 목록을 조회할 때 사용됩니다.
     *
     * @return 회사 목록 DTO 리스트
     */
    public List<CompanyResponseDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Company Entity를 CompanyResponseDto로 변환합니다.
     *
     * @param company Company Entity
     * @return CompanyResponseDto
     */
    private CompanyResponseDto convertToDto(Company company) {
        return new CompanyResponseDto(
                company.getCompanyNo(),
                company.getCompanyName()
        );
    }
}
