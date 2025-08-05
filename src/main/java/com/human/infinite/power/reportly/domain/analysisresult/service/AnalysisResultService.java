package com.human.infinite.power.reportly.domain.analysisresult.service;

import com.human.infinite.power.reportly.common.exception.UserException;
import com.human.infinite.power.reportly.domain.analysisresult.dto.*;
import com.human.infinite.power.reportly.domain.analysisresult.entity.AnalysisResult;
import com.human.infinite.power.reportly.domain.analysisresult.repository.AnalysisResultRepository;
import com.human.infinite.power.reportly.domain.analysisresultscore.entity.AnalysisResultScore;
import com.human.infinite.power.reportly.domain.analysisresultscore.repository.AnalysisResultScoreRepository;
import com.human.infinite.power.reportly.domain.competitor.entity.Competitor;
import com.human.infinite.power.reportly.domain.competitor.repository.CompetitorRepository;
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
    public TotalScoreListResponseDto getTotalScoreList() {
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
}
