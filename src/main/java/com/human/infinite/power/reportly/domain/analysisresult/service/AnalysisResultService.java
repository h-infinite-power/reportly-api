package com.human.infinite.power.reportly.domain.analysisresult.service;

import com.human.infinite.power.reportly.common.exception.UserException;
import com.human.infinite.power.reportly.domain.analysisresult.dto.*;
import com.human.infinite.power.reportly.domain.analysisresult.dto.prompt.CategoryResultDto;
import com.human.infinite.power.reportly.domain.analysisresult.dto.prompt.PromptResponseDto;
import com.human.infinite.power.reportly.domain.analysisresult.entity.AnalysisResult;
import com.human.infinite.power.reportly.domain.analysisresult.repository.AnalysisResultRepository;
import com.human.infinite.power.reportly.domain.analysisresultscore.entity.AnalysisResultScore;
import com.human.infinite.power.reportly.domain.analysisresultscore.repository.AnalysisResultScoreRepository;
import com.human.infinite.power.reportly.domain.category.entity.Category;
import com.human.infinite.power.reportly.domain.category.repository.CategoryRepository;
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
    private final KeywordRepository keywordRepository;
    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;

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
                    question.getCategory().getCategoryName(),
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
