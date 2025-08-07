package com.human.infinite.power.reportly.domain.analysisresult.service;

import com.human.infinite.power.reportly.common.dto.NoResponseDto;
import com.human.infinite.power.reportly.common.exception.UserException;
import com.human.infinite.power.reportly.domain.analysisresult.dto.*;
import com.human.infinite.power.reportly.domain.analysisresult.dto.prompt.CategoryResultDto;
import com.human.infinite.power.reportly.domain.analysisresult.dto.prompt.InsightSummaryDto;
import com.human.infinite.power.reportly.domain.analysisresult.dto.prompt.PromptResponseDto;
import com.human.infinite.power.reportly.domain.analysisresult.entity.AnalysisResult;
import com.human.infinite.power.reportly.domain.analysisresult.repository.AnalysisResultRepository;
import com.human.infinite.power.reportly.domain.analysisresultjob.entity.AnalysisResultJob;
import com.human.infinite.power.reportly.domain.analysisresultjob.repository.AnalysisResultJobRepository;
import com.human.infinite.power.reportly.domain.analysisresultscore.entity.AnalysisResultScore;
import com.human.infinite.power.reportly.domain.analysisresultscore.repository.AnalysisResultScoreRepository;
import com.human.infinite.power.reportly.domain.competitor.entity.Competitor;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * 분석결과 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AnalysisResultService {

    private final AnalysisResultRepository analysisResultRepository;
    private final AnalysisResultScoreRepository analysisResultScoreRepository;
    private final CompetitorRepository competitorRepository;
    private final KeywordRepository keywordRepository;
    private final JobRepository jobRepository;
    private final AnalysisResultJobRepository analysisResultJobRepository;

    /**
     * 분석결과를 생성합니다.
     * 요청한 브랜드명, 경쟁사명, 업종명을 기반으로 분석결과 데이터를 저장합니다.
     *
     * @param requestDto 분석결과 생성 요청 DTO
     * @return 생성된 분석결과 번호
     */
    @Transactional
    public AnalysisResultCreateResponseDto createAnalysisResult(AnalysisResultCreateRequestDto requestDto) {
        LocalDate today = LocalDate.now();
        
        // 타겟 회사와 경쟁사 리스트 조합
        List<Long> allCompanyNos = new ArrayList<>();
        allCompanyNos.add(requestDto.getTargetCompanyNo());
        allCompanyNos.addAll(requestDto.getCompetitorCompanyNoList());
        
        // 이미 분석된 결과가 있는지 확인
        List<AnalysisResult> existingResults = analysisResultRepository
                .findByCompanyNoInAndIndustryNoAndDate(allCompanyNos, requestDto.getIndustryNo(), today);
        
        Set<Long> existingCompanyNos = existingResults.stream()
                .map(AnalysisResult::getCompanyNo)
                .collect(Collectors.toSet());
        
        // 분석이 필요한 회사들 필터링
        List<Long> newCompanyNos = allCompanyNos.stream()
                .filter(companyNo -> !existingCompanyNos.contains(companyNo))
                .collect(Collectors.toList());
        
        Long analysisResultNo = null;
        
        // 새로 분석이 필요한 회사가 있는 경우에만 getResult() 호출
        if (!newCompanyNos.isEmpty()) {
            log.info("새로운 분석이 필요한 회사: {}", newCompanyNos);
            analysisResultNo = getResult(requestDto, newCompanyNos);
        } else {
            // 모든 회사가 이미 분석된 경우 타겟 회사의 분석결과 번호 반환
            analysisResultNo = existingResults.stream()
                    .filter(result -> result.getCompanyNo().equals(requestDto.getTargetCompanyNo()))
                    .findFirst()
                    .map(AnalysisResult::getAnalysisResultNo)
                    .orElseThrow(() -> new UserException("타겟 회사의 분석결과를 찾을 수 없습니다."));
        }
        
        return new AnalysisResultCreateResponseDto(analysisResultNo);
    }

    /**
     * 실제 분석 로직을 수행하는 메서드 (현재는 더미 데이터 생성)
     * 추후 실제 AI 분석 로직으로 대체될 예정
     *
     * @param requestDto 분석결과 생성 요청 DTO
     * @param companyNos 분석할 회사 번호 리스트
     * @return 생성된 분석결과 번호
     */
    @Transactional
    private Long getResult(AnalysisResultCreateRequestDto requestDto, List<Long> companyNos) {
        LocalDate today = LocalDate.now();
        Long targetAnalysisResultNo = null;
        
        for (Long companyNo : companyNos) {
            // 더미 분석결과 생성
            AnalysisResult analysisResult = new AnalysisResult(
                    companyNo,
                    requestDto.getIndustryNo(),
                    today,
                    "AI가 분석한 요약 내용입니다.",
                    "AI가 분석한 상세 내용입니다.",
                    85.5, // 더미 종합 점수
                    "AI 분석 근거 자료",
                    "강점: 브랜드 인지도가 높습니다.",
                    "약점: 가격 경쟁력이 부족합니다.",
                    "개선사항: 마케팅 전략을 개선하세요."
            );
            
            analysisResult = analysisResultRepository.save(analysisResult);
            
            // 타겟 회사인 경우 분석결과 번호 저장
            if (companyNo.equals(requestDto.getTargetCompanyNo())) {
                targetAnalysisResultNo = analysisResult.getAnalysisResultNo();
                
                // 경쟁사 정보 저장
                for (Long competitorCompanyNo : requestDto.getCompetitorCompanyNoList()) {
                    Competitor competitor = new Competitor(
                            analysisResult.getAnalysisResultNo(),
                            competitorCompanyNo
                    );
                    competitorRepository.save(competitor);
                }
            }
            
            // 더미 카테고리별 점수 생성 (예시: 5개 카테고리)
            createDummyScores(analysisResult.getAnalysisResultNo());
            
            // 더미 키워드 생성
            createDummyKeywords(analysisResult.getAnalysisResultNo());
        }
        
        return targetAnalysisResultNo;
    }

    /**
     * 더미 카테고리별 점수를 생성합니다.
     *
     * @param analysisResultNo 분석결과 번호
     */
    @Transactional
    private void createDummyScores(Long analysisResultNo) {
        Random random = new Random();
        Long[] categoryNos = {10L, 11L, 12L, 13L, 14L}; // 더미 카테고리 번호들
        
        for (Long categoryNo : categoryNos) {
            AnalysisResultScore score = new AnalysisResultScore(
                    analysisResultNo,
                    categoryNo,
                    70.0f + random.nextFloat() * 30.0f // 70~100 사이의 랜덤 점수
            );
            analysisResultScoreRepository.save(score);
        }
    }

    /**
     * 더미 키워드를 생성합니다.
     *
     * @param analysisResultNo 분석결과 번호
     */
    @Transactional
    private void createDummyKeywords(Long analysisResultNo) {
        String[] positiveKeywords = {"품질", "혁신", "신뢰", "브랜드", "서비스"};
        String[] negativeKeywords = {"가격", "접근성", "속도", "복잡", "불편"};
        
        // 긍정 키워드 저장
        for (String keyword : positiveKeywords) {
            Keyword keywordEntity = new Keyword(
                    analysisResultNo,
                    "POSITIVE",
                    keyword
            );
            keywordRepository.save(keywordEntity);
        }
        
        // 부정 키워드 저장
        for (String keyword : negativeKeywords) {
            Keyword keywordEntity = new Keyword(
                    analysisResultNo,
                    "NEGATIVE",
                    keyword
            );
            keywordRepository.save(keywordEntity);
        }
    }

    /**
     * 종합 점수 목록을 조회합니다.
     * 현재는 더미 데이터를 반환하며, 추후 실제 데이터베이스 조회 로직으로 대체될 예정입니다.
     *
     * @return 종합 점수 목록 DTO
     */
    public TotalScoreListResponseDto getTotalScoreList(Long analysisResultId) {
        // TODO: 실제 데이터베이스에서 조회하는 로직으로 대체
        // 현재는 더미 데이터 반환
        return new TotalScoreListResponseDto(
                1,        // targetRank
                101L,     // targetCompanyNo  
                87.0,     // targetTotalScore
                76.0,     // competitorAvgTotalScore
                4         // totalCompanyCount
        );
    }

    /**
     * 분석결과 점수 통계를 조회합니다.
     *
     * @param analysisResultId 분석결과 ID
     * @return 점수 통계 데이터
     */
    public AnalysisResultScoreStatisticsResponseDto getAnalysisResultScoreStatistics(Long analysisResultId) {
        // 분석결과 존재 여부 확인
        analysisResultRepository.findById(analysisResultId)
                .orElseThrow(() -> new UserException("분석결과 통계를 찾을 수 없습니다."));
        
        // TODO: 실제 카테고리별 점수 통계 조회 로직 구현
        // 현재는 더미 데이터 반환
        List<CategoryScoreDto> targetScores = Arrays.asList(
                new CategoryScoreDto(10L, "브랜딩", 92.0),
                new CategoryScoreDto(11L, "마케팅", 88.0),
                new CategoryScoreDto(12L, "서비스", 85.0),
                new CategoryScoreDto(13L, "품질", 90.0),
                new CategoryScoreDto(14L, "가격", 75.0)
        );
        
        List<CategoryScoreDto> competitorAvgScores = Arrays.asList(
                new CategoryScoreDto(10L, "브랜딩", 80.0),
                new CategoryScoreDto(11L, "마케팅", 82.0),
                new CategoryScoreDto(12L, "서비스", 78.0),
                new CategoryScoreDto(13L, "품질", 85.0),
                new CategoryScoreDto(14L, "가격", 88.0)
        );
        
        return new AnalysisResultScoreStatisticsResponseDto(
                targetScores,
                competitorAvgScores
        );
    }

    /**
     * 카테고리별 점수를 조회합니다.
     *
     * @param analysisResultId 분석결과 ID
     * @param companyNo 선택된 경쟁사 번호
     * @return 카테고리별 점수 목록
     */
    public List<AnalysisResultScoreResponseDto> getAnalysisResultScores(Long analysisResultId, Long companyNo) {
        // 분석결과 존재 여부 확인
        analysisResultRepository.findById(analysisResultId)
                .orElseThrow(() -> new UserException("카테고리 점수를 찾을 수 없습니다."));
        
        // TODO: 실제 데이터베이스에서 해당 회사의 카테고리별 점수 조회
        // 현재는 더미 데이터 반환
        return Arrays.asList(
                new AnalysisResultScoreResponseDto(10L, "브랜딩", 92.0),
                new AnalysisResultScoreResponseDto(11L, "마케팅", 88.0),
                new AnalysisResultScoreResponseDto(12L, "서비스", 85.0),
                new AnalysisResultScoreResponseDto(13L, "품질", 90.0),
                new AnalysisResultScoreResponseDto(14L, "가격", 75.0)
        );
    }

    /**
     * 분석결과 상세 정보를 조회합니다.
     *
     * @param analysisResultId 분석결과 ID
     * @return 분석결과 상세 정보
     */
    public AnalysisResultDetailResponseDto getAnalysisResultDetail(Long analysisResultId) {
        // 분석결과 조회
        AnalysisResult analysisResult = analysisResultRepository.findById(analysisResultId)
                .orElseThrow(() -> new UserException("분석결과 상세 정보를 찾을 수 없습니다."));
        
        // TODO: 실제 질문/답변 데이터 조회 로직 구현
        // 현재는 더미 데이터 반환
        List<QaDto> qaList = createDummyQaList();
        
        return new AnalysisResultDetailResponseDto(
                analysisResult.getStrongPoint(),
                analysisResult.getWeakPoint(),
                analysisResult.getImprovements(),
                qaList
        );
    }

    /**
     * 더미 질문/답변 데이터를 생성합니다.
     *
     * @return 질문/답변 DTO 리스트
     */
    private List<QaDto> createDummyQaList() {
        List<QaDto> qaList = new ArrayList<>();
        
        // 더미 QA 데이터 생성
        CompanyInfoDto targetCompanyInfo = new CompanyInfoDto(
                1L,
                "삼성전자",
                "브랜딩 전략이 경쟁사 대비 우수합니다.",
                "브랜딩 전략이 경쟁사 대비 우수합니다. 상세 내용...",
                Arrays.asList("반도체", "1등", "기획"),
                Arrays.asList("최악", "사망", "주가폭락"),
                92.0
        );
        
        List<CompanyInfoDto> competitorInfoList = Arrays.asList(
                new CompanyInfoDto(
                        2L,
                        "애플",
                        "혁신적인 디자인으로 유명합니다.",
                        88.0
                )
        );
        
        QaDto qa = new QaDto(
                1L,
                23L,
                "우리 회사의 강점은 무엇인가요?",
                targetCompanyInfo,
                competitorInfoList
        );
        
        qaList.add(qa);
        
        return qaList;
    }

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
