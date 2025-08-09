package com.human.infinite.power.reportly.domain.analysisresult.controller;

import com.human.infinite.power.reportly.common.dto.NoResponseDto;
import com.human.infinite.power.reportly.domain.analysisresult.dto.*;
import com.human.infinite.power.reportly.domain.analysisresult.service.AnalysisResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 분석결과 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/reportly-api/analysis-results")
@RequiredArgsConstructor
public class AnalysisResultController {

    private final AnalysisResultService analysisResultService;

    /**
     * 카테고리별 점수를 조회합니다.
     * 선택된 경쟁사의 카테고리별 점수를 조회합니다.
     *
     * @param analysisResultNo 분석결과 No
     * @param companyNo 선택된 경쟁사 번호
     * @return 카테고리별 점수 목록
     */
    @GetMapping("/{analysisResultNo}/analysis-result-scores")
    public ResponseEntity<List<AnalysisResultScoreResponseDto>> getAnalysisResultScores(
            @PathVariable("analysisResultNo") Long analysisResultNo) {
        List<AnalysisResultScoreResponseDto> response = 
                analysisResultService.getAnalysisResultScores(analysisResultNo);
        return ResponseEntity.ok(response);
    }

    /**
     * 분석결과 상세 정보를 조회합니다.
     * 질문/답변 및 AI 인사이트 요약 상세 정보를 조회합니다.
     *
     * @param analysisResultNo 분석결과 No
     * @return 분석결과 상세 정보
     */
    @GetMapping("/{analysisResultNo}")
    public ResponseEntity<AnalysisResultDetailResponseDto> getAnalysisResultDetail(
            @PathVariable("analysisResultNo") Long analysisResultNo) {
        AnalysisResultDetailResponseDto response = 
                analysisResultService.getAnalysisResultDetail(analysisResultNo);
        return ResponseEntity.ok(response);
    }
}
