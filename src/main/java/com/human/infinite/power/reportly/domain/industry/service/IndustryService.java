package com.human.infinite.power.reportly.domain.industry.service;

import com.human.infinite.power.reportly.common.dto.KeyValueResponseDto;
import com.human.infinite.power.reportly.common.dto.NameRequestDto;
import com.human.infinite.power.reportly.domain.industry.dto.IndustryResponseDto;
import com.human.infinite.power.reportly.domain.industry.entity.Industry;
import com.human.infinite.power.reportly.domain.industry.repository.IndustryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 업종 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IndustryService {

    private final IndustryRepository industryRepository;

    /**
     * 모든 업종 목록을 조회합니다.
     * 메인 페이지에서 selectBox 형태로 업종 목록을 조회할 때 사용됩니다.
     *
     * @return 업종 목록 DTO 리스트
     */
    public List<IndustryResponseDto> getAllIndustries() {
        List<Industry> industries = industryRepository.findAll();
        return industries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * 업종을 생성합니다.
     * 메인 페이지에서 selectBox 목록에 해당하는 부분을 저장하는데 사용됩니다.
     *
     * @param requestDto 이름 요청 DTO
     * @return 키-값 형태의 응답 DTO
     */
    @Transactional
    public KeyValueResponseDto createIndustry(NameRequestDto requestDto) {
        // Industry Entity 생성 (생성자에서 자동으로 ID 생성)
        Industry industry = new Industry(requestDto.getCompanyName());
        
        // 데이터베이스에 저장
        Industry savedIndustry = industryRepository.save(industry);
        
        // 응답 DTO 생성 및 반환
        return new KeyValueResponseDto("industryNo", savedIndustry.getIndustryNo());
    }

    /**
     * Industry Entity를 IndustryResponseDto로 변환합니다.
     *
     * @param industry Industry Entity
     * @return IndustryResponseDto
     */
    private IndustryResponseDto convertToDto(Industry industry) {
        return new IndustryResponseDto(
                industry.getIndustryNo(),
                industry.getIndustryName()
        );
    }
}
