package com.human.infinite.power.reportly.domain.industry.controller;

import com.human.infinite.power.reportly.common.dto.KeyValueResponseDto;
import com.human.infinite.power.reportly.common.dto.NameRequestDto;
import com.human.infinite.power.reportly.domain.industry.dto.IndustryResponseDto;
import com.human.infinite.power.reportly.domain.industry.service.IndustryService;
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
 * 업종 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/reportly-api/industries")
@RequiredArgsConstructor
public class IndustryController {

    private final IndustryService industryService;

    /**
     * 업종을 생성합니다.
     * 메인 페이지에서 selectBox 목록에 해당하는 부분을 저장하는데 사용됩니다.
     *
     * @param requestDto 이름 요청 DTO
     * @return 생성된 업종 정보
     */
    @PostMapping
    public ResponseEntity<KeyValueResponseDto> createIndustry(@RequestBody NameRequestDto requestDto) {
        if (requestDto == null || requestDto.getName() == null || requestDto.getName().trim().isEmpty()) {
            throw new UserException("업종 이름은 필수입니다.");
        }
        KeyValueResponseDto response = industryService.createIndustry(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

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
