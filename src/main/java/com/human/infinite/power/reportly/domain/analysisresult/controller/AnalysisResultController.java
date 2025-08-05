package com.human.infinite.power.reportly.domain.analysisresult.controller;

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
     * 분석결과를 생성합니다.
     * 요청한 브랜드명, 경쟁사명, 업종명을 기반으로 분석결과 데이터를 저장합니다.
     *
     * @param requestDto 분석결과 생성 요청 DTO
     * @return 생성된 분석결과 번호
     */
    @PostMapping
    public ResponseEntity<AnalysisResultCreateResponseDto> createAnalysisResult(
            @RequestBody AnalysisResultCreateRequestDto requestDto) {
        AnalysisResultCreateResponseDto response = analysisResultService.createAnalysisResult(requestDto);
        return ResponseEntity.ok(response);
    }

    /**
     * 종합 점수 목록을 조회합니다.
     * 경쟁사와 우리 회사의 절대 점수를 비교한 데이터를 반환합니다.
     *
     * @return 종합 점수 목록
     */
    @GetMapping("/total-score-list")
    public ResponseEntity<TotalScoreListResponseDto> getTotalScoreList() {
        TotalScoreListResponseDto response = analysisResultService.getTotalScoreList();
        return ResponseEntity.ok(response);
    }

    /**
     * 분석결과 점수 통계를 조회합니다.
     * 경쟁사 및 타겟 회사의 카테고리별 평균 점수 통계 데이터를 반환합니다.
     *
     * @param analysisResultId 분석결과 ID
     * @return 점수 통계 데이터
     */
    @GetMapping("/{analysisResultId}/analysis-result-score-statistics")
    public ResponseEntity<AnalysisResultScoreStatisticsResponseDto> getAnalysisResultScoreStatistics(
            @PathVariable("analysisResultId") Long analysisResultId) {
        AnalysisResultScoreStatisticsResponseDto response = 
                analysisResultService.getAnalysisResultScoreStatistics(analysisResultId);
        return ResponseEntity.ok(response);
    }

    /**
     * 카테고리별 점수를 조회합니다.
     * 선택된 경쟁사의 카테고리별 점수를 조회합니다.
     *
     * @param analysisResultId 분석결과 ID
     * @param companyNo 선택된 경쟁사 번호
     * @return 카테고리별 점수 목록
     */
    @GetMapping("/{analysisResultId}/analysis-result-scores")
    public ResponseEntity<List<AnalysisResultScoreResponseDto>> getAnalysisResultScores(
            @PathVariable("analysisResultId") Long analysisResultId,
            @RequestParam("companyNo") Long companyNo) {
        List<AnalysisResultScoreResponseDto> response = 
                analysisResultService.getAnalysisResultScores(analysisResultId, companyNo);
        return ResponseEntity.ok(response);
    }

    /**
     * 분석결과 상세 정보를 조회합니다.
     * 질문/답변 및 AI 인사이트 요약 상세 정보를 조회합니다.
     *
     * @param analysisResultId 분석결과 ID
     * @return 분석결과 상세 정보
     */
    @GetMapping("/{analysisResultId}")
    public ResponseEntity<AnalysisResultDetailResponseDto> getAnalysisResultDetail(
            @PathVariable("analysisResultId") Long analysisResultId) {
        AnalysisResultDetailResponseDto response = 
                analysisResultService.getAnalysisResultDetail(analysisResultId);
        return ResponseEntity.ok(response);
    }
}
