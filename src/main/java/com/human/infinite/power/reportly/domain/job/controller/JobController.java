package com.human.infinite.power.reportly.domain.job.controller;

import com.human.infinite.power.reportly.common.dto.NoResponseDto;
import com.human.infinite.power.reportly.domain.analysisresult.dto.AnalysisResultCreateRequestDto;
import com.human.infinite.power.reportly.domain.job.dto.TotalScoreListResponseDto;
import com.human.infinite.power.reportly.domain.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reportly-api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    /**
     * 분석결과를 생성합니다.
     * 요청한 브랜드명, 경쟁사명, 업종명을 기반으로 분석결과 데이터를 저장합니다.
     *
     * @param requestDto 분석결과 생성 요청 DTO
     * @return 생성된 분석결과 번호
     */
    @PostMapping
    public ResponseEntity<NoResponseDto> createJobs(
        @RequestBody AnalysisResultCreateRequestDto requestDto) {

        NoResponseDto response = jobService.doAnalysis(requestDto);
        return ResponseEntity.ok(response);
    }

    /**
     * 종합 점수 목록을 조회합니다.
     * 경쟁사와 우리 회사의 절대 점수를 비교한 데이터를 반환합니다.
     *
     * @param jobNo 작업 번호
     * @return 종합 점수 목록
     */
    @GetMapping("/{jobNo}/total-score-list")
    public ResponseEntity<TotalScoreListResponseDto> getTotalScoreList(@PathVariable("jobNo") Long jobNo) {
        TotalScoreListResponseDto response = jobService.getTotalScoreList(jobNo);
        return ResponseEntity.ok(response);
    }
}
