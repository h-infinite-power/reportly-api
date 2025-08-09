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
import com.human.infinite.power.reportly.domain.category.entity.Category;
import com.human.infinite.power.reportly.domain.category.repository.CategoryRepository;
import com.human.infinite.power.reportly.domain.competitor.entity.Competitor;
import com.human.infinite.power.reportly.domain.competitor.repository.CompetitorRepository;
import com.human.infinite.power.reportly.domain.job.entity.Job;
import com.human.infinite.power.reportly.domain.job.repository.JobRepository;
import com.human.infinite.power.reportly.domain.keyword.entity.Keyword;
import com.human.infinite.power.reportly.domain.keyword.repository.KeywordRepository;
import com.human.infinite.power.reportly.domain.question.entity.Question;
import com.human.infinite.power.reportly.domain.question.repository.QuestionRepository;
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
    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;



    /**
     * 더미 카테고리별 점수를 생성합니다.
     *
     * @param analysisResultNo 분석결과 번호
     */
    @Transactional
    public void createDummyScores(Long analysisResultNo) {
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
    public void createDummyKeywords(Long analysisResultNo) {
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
    public TotalScoreListResponseDto getTotalScoreList(Long analysisResultNo) {
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
     * @param analysisResultNo 분석결과 No
     * @return 점수 통계 데이터
     */
    public AnalysisResultScoreStatisticsResponseDto getAnalysisResultScoreStatistics(Long analysisResultNo) {
        // 분석결과 존재 여부 확인
        analysisResultRepository.findById(analysisResultNo)
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
     * @param analysisResultNo 분석결과 번호
     * @return 카테고리별 점수 목록
     */
    public List<AnalysisResultScoreResponseDto> getAnalysisResultScores(Long analysisResultNo) {
        // 분석결과 존재 여부 확인
        analysisResultRepository.findById(analysisResultNo)
                .orElseThrow(() -> new UserException("카테고리 점수를 찾을 수 없습니다."));
        
        // 카테고리별 점수 조회
        List<AnalysisResultScore> scores = analysisResultScoreRepository
                .findByAnalysisResultNoWithCategoryOrderByCategoryName(analysisResultNo);
        
        // DTO 변환
        return scores.stream()
                .map(score -> new AnalysisResultScoreResponseDto(
                        score.getCategoryNo(),
                        score.getCategory().getCategoryName(),
                        score.getCategoryScore().doubleValue()
                ))
                .collect(Collectors.toList());
    }

    /**
     * 분석결과 상세 정보를 조회합니다.
     *
     * @param analysisResultId 분석결과 ID
     * @return 분석결과 상세 정보
     */
    public AnalysisResultDetailResponseDto getAnalysisResultDetail(Long analysisResultNo) {
        // 1) 분석결과 및 기본 정보 조회
        AnalysisResult analysisResult = analysisResultRepository
                .findByAnalysisResultNoWithCompanyAndIndustry(analysisResultNo)
                .orElseThrow(() -> new UserException("분석결과 상세 정보를 찾을 수 없습니다."));

        Long industryNo = analysisResult.getIndustryNo();
        Long targetCompanyNo = analysisResult.getCompanyNo();
        java.time.LocalDate analysisDate = analysisResult.getDate();

        // 2) 질문 목록 조회 (업종 기준)
        List<Question> questionEntityList = questionRepository.findByIndustryNoWithCategory(industryNo);

        // 3) 키워드 조회 (긍정/부정)
        List<String> positiveKeywords = keywordRepository
                .findByAnalysisResultNoAndType(analysisResultNo, "POSITIVE")
                .stream()
                .map(Keyword::getContent)
                .collect(Collectors.toList());
        List<String> negativeKeywords = keywordRepository
                .findByAnalysisResultNoAndType(analysisResultNo, "NEGATIVE")
                .stream()
                .map(Keyword::getContent)
                .collect(Collectors.toList());

        // 4) 같은 업종·일자의 경쟁사 분석결과 조회
        List<AnalysisResult> competitorResults = analysisResultRepository
                .findByIndustryNoAndDate(industryNo, analysisDate)
                .stream()
                .filter(ar -> !ar.getAnalysisResultNo().equals(analysisResultNo))
                .collect(Collectors.toList());

        // 5) 카테고리별로 QA DTO 생성
        List<QaDto> qaDtoList = new java.util.ArrayList<>();
        for (Question question : questionEntityList) {
            Long categoryNo = question.getCategoryNo();

            // 5-1) 타겟 회사 카테고리 점수 조회
            AnalysisResultScore targetScoreEntity = analysisResultScoreRepository
                    .findFirstByAnalysisResultNoAndCategoryNo(analysisResultNo, categoryNo);
            Double targetCategoryScore = targetScoreEntity != null ? targetScoreEntity.getCategoryScore().doubleValue() : null;

            // 5-2) 타겟 회사 정보 DTO
            CompanyInfoDto targetCompanyInfo = new CompanyInfoDto(
                    targetCompanyNo,
                    analysisResult.getCompany().getCompanyName(),
                    analysisResult.getSummary(),
                    analysisResult.getContent(),
                    positiveKeywords,
                    negativeKeywords,
                    targetCategoryScore
            );

            // 5-3) 경쟁사 정보 목록
            List<CompanyInfoDto> competitorInfoList = competitorResults.stream()
                    .map(cr -> {
                        AnalysisResultScore compScore = analysisResultScoreRepository
                                .findFirstByAnalysisResultNoAndCategoryNo(cr.getAnalysisResultNo(), categoryNo);
                        Double compCatScore = compScore != null ? compScore.getCategoryScore().doubleValue() : null;
                        return new CompanyInfoDto(
                                cr.getCompanyNo(),
                                cr.getCompany().getCompanyName(),
                                cr.getSummary(),
                                compCatScore
                        );
                    })
                    .collect(Collectors.toList());

            // 5-4) QA DTO 생성
            QaDto qaDto = new QaDto(
                    question.getQuestionNo(),
                    categoryNo,
                    question.getQuestion(),
                    targetCompanyInfo,
                    competitorInfoList
            );
            qaDtoList.add(qaDto);
        }

        // 6) 응답 DTO 반환
        return new AnalysisResultDetailResponseDto(
                analysisResult.getStrongPoint(),
                analysisResult.getWeakPoint(),
                analysisResult.getImprovements(),
                qaDtoList
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

        Map<String, Long> categoryNameMap = categoryRepository.findAll().stream().collect(Collectors.toMap(Category::getCategoryName, Category::getCategoryNo));

        for (CategoryResultDto categoryResult : categoryResults) {
            Long categoryNo = Optional.ofNullable(categoryNameMap.get(categoryResult.getCategory()))
                .orElseThrow(() -> new RuntimeException("AI가 잘못된 카테고리명 응답을 줬습니다. 프롬프트를 수정해 주세요. \n존재하지 않는 카테고리명 : " + categoryResult.getCategory()));
            AnalysisResultScore score = new AnalysisResultScore(
                analysisResultNo,
                categoryNo,
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

}
