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
     * 종합 점수 목록을 조회합니다.
     * 경쟁사와 우리 회사의 절대 점수를 비교한 데이터를 반환합니다.
     *
     * @return 종합 점수 목록
     */
    @GetMapping("/{analysisResultNo}/total-score-list")
    public ResponseEntity<TotalScoreListResponseDto> getTotalScoreList(@PathVariable("analysisResultNo") Long analysisResultNo) {
        TotalScoreListResponseDto response = analysisResultService.getTotalScoreList(analysisResultNo);
        return ResponseEntity.ok(response);
    }

    /**
     * 분석결과 점수 통계를 조회합니다.
     * 경쟁사 및 타겟 회사의 카테고리별 평균 점수 통계 데이터를 반환합니다.
     *
     * @param analysisResultNo 분석결과 No
     * @return 점수 통계 데이터
     */
    @GetMapping("/{analysisResultNo}/analysis-result-score-statistics")
    public ResponseEntity<AnalysisResultScoreStatisticsResponseDto> getAnalysisResultScoreStatistics(
            @PathVariable("analysisResultNo") Long analysisResultNo) {
        AnalysisResultScoreStatisticsResponseDto response = 
                analysisResultService.getAnalysisResultScoreStatistics(analysisResultNo);
        return ResponseEntity.ok(response);
    }

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
