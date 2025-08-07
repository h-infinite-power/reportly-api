package com.human.infinite.power.reportly.domain.job.service;

import com.human.infinite.power.reportly.common.dto.NoResponseDto;
import com.human.infinite.power.reportly.common.exception.UserException;
import com.human.infinite.power.reportly.domain.analysisresult.dto.AnalysisResultCreateRequestDto;
import com.human.infinite.power.reportly.domain.analysisresult.dto.prompt.CategoryResultDto;
import com.human.infinite.power.reportly.domain.analysisresult.dto.prompt.InsightSummaryDto;
import com.human.infinite.power.reportly.domain.analysisresult.dto.prompt.PromptResponseDto;
import com.human.infinite.power.reportly.domain.analysisresult.entity.AnalysisResult;
import com.human.infinite.power.reportly.domain.analysisresult.repository.AnalysisResultRepository;
import com.human.infinite.power.reportly.domain.analysisresultjob.entity.AnalysisResultJob;
import com.human.infinite.power.reportly.domain.analysisresultjob.repository.AnalysisResultJobRepository;
import com.human.infinite.power.reportly.domain.analysisresultscore.entity.AnalysisResultScore;
import com.human.infinite.power.reportly.domain.analysisresultscore.repository.AnalysisResultScoreRepository;
import com.human.infinite.power.reportly.domain.competitor.repository.CompetitorRepository;
import com.human.infinite.power.reportly.domain.job.entity.Job;
import com.human.infinite.power.reportly.domain.job.repository.JobRepository;
import com.human.infinite.power.reportly.domain.keyword.entity.Keyword;
import com.human.infinite.power.reportly.domain.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 업무 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class JobService {
    
    private final JobRepository jobRepository;
    private final AnalysisResultRepository analysisResultRepository;
    private final AnalysisResultScoreRepository analysisResultScoreRepository;
    private final KeywordRepository keywordRepository;
    private final AnalysisResultJobRepository analysisResultJobRepository;

    /**
     * 분석을 수행하고 작업을 생성합니다.
     * POST /reportly-api/analysis-results API 호출시 사용되는 메서드입니다.
     *
     * @param targetCompanyNo 타겟 회사 번호
     * @param competitorCompanyNoList 경쟁사 회사 번호 리스트
     * @return jobNo를 포함한 응답 DTO
     */
    @Transactional
    public NoResponseDto doAnalysis(AnalysisResultCreateRequestDto analysisResultCreateRequestDto) {
        final Long targetCompanyNo = analysisResultCreateRequestDto.getTargetCompanyNo();
        final Long industryNo = analysisResultCreateRequestDto.getIndustryNo();
        final List<Long> competitorCompanyNoList = analysisResultCreateRequestDto.getCompetitorCompanyNoList();

        log.info("분석 시작 - 타겟회사: {}, 경쟁사: {}", targetCompanyNo, competitorCompanyNoList);

        // 대상 회사 분석결과 저장
        AnalysisResult targetCompanyAnalysisResult = saveAnalysisResult(targetCompanyNo, industryNo);

        // 경쟁회사 분석결과 저장
        List<AnalysisResult> companyAnalysisResultList = new ArrayList<>();
        for (Long competitorCompanyNo : competitorCompanyNoList) {
            AnalysisResult competitorResult = saveAnalysisResult(competitorCompanyNo, industryNo);
            companyAnalysisResultList.add(competitorResult);
        }

        // Job 생성 및 저장 (타겟 회사의 분석결과를 기준으로)
        Job job = new Job(targetCompanyAnalysisResult.getAnalysisResultNo());
        job = jobRepository.save(job);
        analysisResultJobRepository.save(new AnalysisResultJob(job.getJobNo(), targetCompanyAnalysisResult.getAnalysisResultNo()));

        log.info("분석 완료 - 작업번호: {}", job.getJobNo());
        return NoResponseDto.of("jobNo", job.getJobNo());
    }

    /**
     * 분석결과를 저장합니다. 이미 존재한다면 기존 값을 반환합니다.
     *
     * @param companyNo  회사 번호
     * @param industryNo 업종 번호
     * @return 저장된 또는 기존 분석결과
     */
    @Transactional
    public AnalysisResult saveAnalysisResult(Long companyNo, Long industryNo) {
        // 최신 결과 조회
        AnalysisResult existingResult = getLatestAnalysisResult(companyNo, industryNo);

        if (Objects.nonNull(existingResult)) {
            log.info("기존 분석결과 반환 - 회사: {}", companyNo);
            return existingResult;
        }

        if (true) {
            throw new UserException("현재 MVP 버전에서는 직접적인 API를 통한 분석결과 생성을 지원하지 않습니다.\n개발자에게 문의주세요.");
        }

        // 새로운 분석결과 생성
        log.info("새로운 분석결과 생성 - 회사: {}", companyNo);
        PromptResponseDto promptResponse = getPromptResponse(companyNo);
        return saveAnalysisResultFromPrompt(companyNo, industryNo, promptResponse);
    }

    /**
     * 최신 분석결과를 조회합니다 (당일 기준).
     *
     * 존재하지 않을 시 Null 반환
     * 현재는 최신버전을 가져오지만 차후에는 요금제에 따라 과거버전을 보여주는 등의 처리가 필요함.
     *
     * @param companyNo  회사 번호
     * @param industryNo 업종 번호
     * @return 분석결과 (없으면 null)
     */
    private AnalysisResult getLatestAnalysisResult(Long companyNo, Long industryNo) {

        return analysisResultRepository
            .findByCompanyNoAndIndustryNoOrderByCreatedAtDesc(companyNo, industryNo).stream().findFirst().orElse(null);
    }

    /**
     * 프롬프트 응답을 기반으로 분석결과를 저장합니다.
     *
     * @param companyNo      회사 번호
     * @param industryNo
     * @param promptResponse 프롬프트 응답 DTO
     * @return 저장된 분석결과
     */
    @Transactional
    public AnalysisResult saveAnalysisResultFromPrompt(Long companyNo, Long industryNo, PromptResponseDto promptResponse) {
        LocalDate today = LocalDate.now();

        // 전체 점수 계산 (카테고리별 점수의 평균)
        double totalScore = promptResponse.getCategoryResults().stream()
            .mapToInt(CategoryResultDto::getScore)
            .average()
            .orElse(0.0);

        // AnalysisResult 저장
        AnalysisResult analysisResult = new AnalysisResult(
            companyNo,
            industryNo,
            today,
            "AI가 분석한 " + promptResponse.getBrand() + " 브랜드 요약",
            "AI가 분석한 " + promptResponse.getBrand() + " 브랜드 상세 분석 내용",
            totalScore,
            "AI 분석 근거 자료",
            promptResponse.getInsightSummary().getStrengths(),
            promptResponse.getInsightSummary().getWeaknesses(),
            promptResponse.getInsightSummary().getRecommendations()
        );

        analysisResult = analysisResultRepository.save(analysisResult);

        // AnalysisResultScore 저장
        saveAnalysisResultScores(analysisResult.getAnalysisResultNo(), promptResponse.getCategoryResults());

        // Keyword 저장
        saveKeywords(analysisResult.getAnalysisResultNo(), promptResponse.getCategoryResults());

        return analysisResult;
    }

    /**
     * 분석결과 점수들을 저장합니다.
     *
     * @param analysisResultNo 분석결과 번호
     * @param categoryResults 카테고리 결과 목록
     */
    @Transactional
    public void saveAnalysisResultScores(Long analysisResultNo, List<CategoryResultDto> categoryResults) {
        for (CategoryResultDto categoryResult : categoryResults) {
            AnalysisResultScore score = new AnalysisResultScore(
                analysisResultNo,
                categoryResult.getQuestionId(), // 카테고리 번호로 사용
                categoryResult.getScore().floatValue()
            );
            analysisResultScoreRepository.save(score);
        }
    }

    /**
     * 키워드들을 저장합니다.
     *
     * @param analysisResultNo 분석결과 번호
     * @param categoryResults 카테고리 결과 목록
     */
    @Transactional
    public void saveKeywords(Long analysisResultNo, List<CategoryResultDto> categoryResults) {
        for (CategoryResultDto categoryResult : categoryResults) {
            // 긍정 키워드 저장
            for (String keyword : categoryResult.getPositiveKeyword()) {
                Keyword keywordEntity = new Keyword(
                    analysisResultNo,
                    "POSITIVE",
                    keyword
                );
                keywordRepository.save(keywordEntity);
            }

            // 부정 키워드 저장
            for (String keyword : categoryResult.getNegativeKeyword()) {
                Keyword keywordEntity = new Keyword(
                    analysisResultNo,
                    "NEGATIVE",
                    keyword
                );
                keywordRepository.save(keywordEntity);
            }
        }
    }

    /**
     * AI 프롬프트 응답을 모방한 더미 데이터를 생성합니다.
     * 추후 실제 AI 서빙서버 연동시 이 메서드를 대체할 예정입니다.
     *
     * @param companyNo 회사 번호
     * @return 프롬프트 응답 DTO
     */
    private PromptResponseDto getPromptResponse(Long companyNo) {
        // 더미 카테고리 결과 생성 (7개 질문)
        List<CategoryResultDto> categoryResults = Arrays.asList(
            new CategoryResultDto(10L, "인지도", "이 브랜드는 다양한 디지털 채널과 대중 매체를 통해 강력한 인지도를 확보하고 있으며...",
                                  Arrays.asList("광고 노출", "앱 다운로드", "검색 유입"), Arrays.asList("브랜드 혼동", "세대별 편차", "해외 인지도 낮음"), 82, 0.91, "광고 노출과 앱 다운로드 수가 경쟁사 대비 높고, 리뷰 수에서도 강세를 보임."),
            new CategoryResultDto(11L, "브랜딩", "브랜드 아이덴티티가 명확하고 일관성 있는 메시지를 전달하고 있습니다.",
                                  Arrays.asList("브랜드파워", "일관성", "차별화"), Arrays.asList("복잡성", "이해도부족"), 85, 0.88, "브랜드 조사 결과"),
            new CategoryResultDto(12L, "마케팅", "디지털 마케팅 전략이 효과적이며 타겟 고객에게 잘 도달하고 있습니다.",
                                  Arrays.asList("디지털전략", "타겟팅", "효과적"), Arrays.asList("비용", "ROI저조"), 78, 0.86, "마케팅 성과 데이터"),
            new CategoryResultDto(13L, "서비스", "고객 서비스 품질이 우수하며 만족도가 높습니다.",
                                  Arrays.asList("품질", "만족도", "응답속도"), Arrays.asList("대기시간", "복잡함"), 80, 0.90, "고객 만족도 조사"),
            new CategoryResultDto(14L, "품질", "제품 품질이 경쟁사 대비 우수하며 지속적인 개선이 이루어지고 있습니다.",
                                  Arrays.asList("우수성", "개선", "안정성"), Arrays.asList("가격대비", "일부결함"), 88, 0.95, "품질 평가 보고서"),
            new CategoryResultDto(15L, "가격", "가격 경쟁력은 다소 부족하지만 프리미엄 포지셔닝으로 차별화하고 있습니다.",
                                  Arrays.asList("프리미엄", "차별화"), Arrays.asList("비싼가격", "경쟁력부족", "부담"), 72, 0.85, "가격 비교 분석"),
            new CategoryResultDto(16L, "혁신", "지속적인 기술 혁신과 신제품 개발로 시장을 선도하고 있습니다.",
                                  Arrays.asList("기술혁신", "신제품", "선도"), Arrays.asList("속도느림", "보수적"), 90, 0.93, "혁신 성과 평가")
        );

        // 더미 인사이트 요약 생성
        InsightSummaryDto insightSummary = new InsightSummaryDto(
            "브랜드 인지도와 제품 품질, 혁신성 측면에서 강력한 경쟁 우위를 보유하고 있으며, 브랜딩 전략이 효과적입니다.",
            "가격 경쟁력이 다소 부족하고, 마케팅 ROI 개선이 필요하며, 고객 서비스의 대기시간 단축이 요구됩니다.",
            "가격 정책 재검토를 통한 경쟁력 강화, 디지털 마케팅 효율성 개선, 고객 서비스 프로세스 개선을 제안합니다."
        );

        return new PromptResponseDto("Company" + companyNo, categoryResults, insightSummary);
    }

}
