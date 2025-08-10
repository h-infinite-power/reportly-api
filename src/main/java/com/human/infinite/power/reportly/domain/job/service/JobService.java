package com.human.infinite.power.reportly.domain.job.service;

import com.human.infinite.power.reportly.common.dto.NoResponseDto;
import com.human.infinite.power.reportly.common.exception.UserException;
import com.human.infinite.power.reportly.domain.analysisresult.dto.AnalysisResultCreateRequestDto;
import com.human.infinite.power.reportly.domain.analysisresult.service.AnalysisResultService;
import com.human.infinite.power.reportly.domain.job.dto.TotalScoreListResponseDto;
import com.human.infinite.power.reportly.domain.job.dto.AnalysisResultScoreStatisticsResponseDto;
import com.human.infinite.power.reportly.domain.job.dto.AnalysisResultInfoResponseDto;
import com.human.infinite.power.reportly.domain.job.dto.CategoryScoreDto;
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
import com.human.infinite.power.reportly.domain.job.entity.Job;
import com.human.infinite.power.reportly.domain.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

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
    private final AnalysisResultJobRepository analysisResultJobRepository;
    private final CategoryRepository categoryRepository;
    private final AnalysisResultService analysisResultService;

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
        List<AnalysisResult> competitorCompanyAnalysisResultList = new ArrayList<>();
        for (Long competitorCompanyNo : competitorCompanyNoList) {
            AnalysisResult competitorResult = saveAnalysisResult(competitorCompanyNo, industryNo);
            competitorCompanyAnalysisResultList.add(competitorResult);
        }

        // Job 생성 및 저장 (타겟 회사의 분석결과를 기준으로)
        Job job = new Job(targetCompanyAnalysisResult.getAnalysisResultNo());
        job = jobRepository.save(job);
        final Long jobNo = job.getJobNo();
        competitorCompanyAnalysisResultList.forEach(
            competitorResult -> analysisResultJobRepository.save(new AnalysisResultJob(jobNo, competitorResult.getAnalysisResultNo()))
        );

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
        return analysisResultService.saveAnalysisResultFromPrompt(companyNo, industryNo, promptResponse);
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
     * 종합 점수 목록을 조회합니다.
     * 경쟁사와 우리 회사의 절대 점수를 비교한 데이터를 반환합니다.
     *
     * @param jobNo 작업 번호
     * @return 종합 점수 목록 응답 DTO
     */
    public TotalScoreListResponseDto getTotalScoreList(Long jobNo) {
        // Job 존재 여부 확인
        Job job = jobRepository.findById(jobNo)
                .orElseThrow(() -> new UserException("분석결과를 찾을 수 없습니다."));
        
        // 해당 Job에 연결된 모든 AnalysisResult 조회
        List<AnalysisResultJob> analysisResultJobs = analysisResultJobRepository.findByJobNo(jobNo);
        
        if (analysisResultJobs.isEmpty()) {
            throw new UserException("분석결과를 찾을 수 없습니다.");
        }
        
        // AnalysisResult 목록 조회
        List<Long> analysisResultNos = analysisResultJobs.stream()
                .map(AnalysisResultJob::getAnalysisResultNo)
                .collect(Collectors.toList());
        
        List<AnalysisResult> competitorResults = analysisResultRepository.findAllById(analysisResultNos);

        final AnalysisResult targetResult = job.getAnalysisResult();
        List<AnalysisResult> allAnalysisResults = competitorResults.stream().collect(Collectors.toList());
        allAnalysisResults.add(targetResult);

        // 모든 회사 점수로 순위 계산
        List<AnalysisResult> allResults = new ArrayList<>(allAnalysisResults);
        allResults.sort(Comparator.comparing(AnalysisResult::getCompanyIndustryTotalScore, Comparator.reverseOrder()));

        // 타겟 회사 순위 찾기
        int targetRank = 1;
        for (int i = 0; i < allResults.size(); i++) {
            if (allResults.get(i).getAnalysisResultNo().equals(targetResult.getAnalysisResultNo())) {
                targetRank = i + 1;
                break;
            }
        }
        
        // 경쟁사 평균 점수 계산
        double competitorAvgScore = competitorResults.stream()
                .mapToDouble(AnalysisResult::getCompanyIndustryTotalScore)
                .average()
                .orElse(0.0);
        
         return new TotalScoreListResponseDto(
                 targetRank,
                 targetResult.getCompanyNo(),
                 targetResult.getCompanyIndustryTotalScore(),
                 competitorAvgScore,
                 allResults.size()
         );
     }

     /**
      * 분석결과 점수 통계를 조회합니다.
      * 경쟁사 및 타겟 회사의 카테고리별 평균 점수 통계 데이터를 반환합니다.
      *
      * @param jobNo 작업 번호
      * @return 분석결과 점수 통계 응답 DTO
      */
     public AnalysisResultScoreStatisticsResponseDto getAnalysisResultScoreStatistics(Long jobNo) {
         // Job 존재 여부 확인
         Job job = jobRepository.findById(jobNo)
                 .orElseThrow(() -> new UserException("분석결과 통계를 찾을 수 없습니다."));
         
         // 해당 Job에 연결된 모든 AnalysisResultJob 조회
         List<AnalysisResultJob> analysisResultJobs = analysisResultJobRepository.findByJobNo(jobNo);
         if (analysisResultJobs.isEmpty()) {
             throw new UserException("분석결과 통계를 찾을 수 없습니다.");
         }
         
         // 모든 연관된 AnalysisResult ID 목록
         List<Long> analysisResultNos = analysisResultJobs.stream()
                 .map(AnalysisResultJob::getAnalysisResultNo)
                 .collect(Collectors.toList());
         analysisResultNos.add(job.getAnalysisResultNo());
         
         // 모든 점수와 카테고리 정보 한 번에 조회
         List<AnalysisResultScore> allScores = analysisResultScoreRepository.findAllByAnalysisResultNoIn(analysisResultNos);
         List<Category> allCategories = categoryRepository.findAll();
         Map<Long, String> categoryNameMap = allCategories.stream()
                 .collect(Collectors.toMap(Category::getCategoryNo, Category::getCategoryName));
         
         // 타겟 회사의 카테고리별 점수
         List<CategoryScoreDto> targetCategoryScores = allScores.stream()
                 .filter(score -> score.getAnalysisResultNo().equals(job.getAnalysisResultNo()))
                 .map(score -> new CategoryScoreDto(
                         score.getCategoryNo(),
                         categoryNameMap.get(score.getCategoryNo()),
                         score.getCategoryScore().doubleValue()))
                 .collect(Collectors.toList());
         
         // 경쟁사들의 카테고리별 평균 점수
         Map<Long, List<Float>> competitorScoresByCategory = allScores.stream()
                 .filter(score -> !score.getAnalysisResultNo().equals(job.getAnalysisResultNo()))
                 .collect(Collectors.groupingBy(
                         AnalysisResultScore::getCategoryNo,
                         Collectors.mapping(AnalysisResultScore::getCategoryScore, Collectors.toList())
                 ));
         
         List<CategoryScoreDto> competitorAvgCategoryScores = competitorScoresByCategory.entrySet().stream()
                 .map(entry -> {
                     double avgScore = entry.getValue().stream()
                             .mapToDouble(Float::doubleValue)
                             .average()
                             .orElse(0.0);
                     return new CategoryScoreDto(
                             entry.getKey(),
                             categoryNameMap.get(entry.getKey()),
                             avgScore
                     );
                 })
                 .collect(Collectors.toList());
         
         return new AnalysisResultScoreStatisticsResponseDto(targetCategoryScores, competitorAvgCategoryScores);
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
            new CategoryResultDto(10L, "인지도", "", "이 브랜드는 다양한 디지털 채널과 대중 매체를 통해 강력한 인지도를 확보하고 있으며...",
                                  Arrays.asList("광고 노출", "앱 다운로드", "검색 유입"), Arrays.asList("브랜드 혼동", "세대별 편차", "해외 인지도 낮음"), 82, 0.91, "광고 노출과 앱 다운로드 수가 경쟁사 대비 높고, 리뷰 수에서도 강세를 보임."),
            new CategoryResultDto(11L, "브랜딩", "","브랜드 아이덴티티가 명확하고 일관성 있는 메시지를 전달하고 있습니다.",
                                  Arrays.asList("브랜드파워", "일관성", "차별화"), Arrays.asList("복잡성", "이해도부족"), 85, 0.88, "브랜드 조사 결과"),
            new CategoryResultDto(12L, "마케팅", "","디지털 마케팅 전략이 효과적이며 타겟 고객에게 잘 도달하고 있습니다.",
                                  Arrays.asList("디지털전략", "타겟팅", "효과적"), Arrays.asList("비용", "ROI저조"), 78, 0.86, "마케팅 성과 데이터"),
            new CategoryResultDto(13L, "서비스", "","고객 서비스 품질이 우수하며 만족도가 높습니다.",
                                  Arrays.asList("품질", "만족도", "응답속도"), Arrays.asList("대기시간", "복잡함"), 80, 0.90, "고객 만족도 조사"),
            new CategoryResultDto(14L, "품질", "","제품 품질이 경쟁사 대비 우수하며 지속적인 개선이 이루어지고 있습니다.",
                                  Arrays.asList("우수성", "개선", "안정성"), Arrays.asList("가격대비", "일부결함"), 88, 0.95, "품질 평가 보고서"),
            new CategoryResultDto(15L, "가격", "","가격 경쟁력은 다소 부족하지만 프리미엄 포지셔닝으로 차별화하고 있습니다.",
                                  Arrays.asList("프리미엄", "차별화"), Arrays.asList("비싼가격", "경쟁력부족", "부담"), 72, 0.85, "가격 비교 분석"),
            new CategoryResultDto(16L, "혁신", "","지속적인 기술 혁신과 신제품 개발로 시장을 선도하고 있습니다.",
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

    /**
     * 분석결과 정보 목록을 조회합니다.
     * 카테고리별 점수의 select box를 위한 회사명, analysisResultNo 목록을 반환합니다.
     * 
     * @param jobNo 작업 번호
     * @return 분석결과 정보 목록
     */
    public List<AnalysisResultInfoResponseDto> getAnalysisResultsInfo(Long jobNo) {
        // 1) Job 존재 여부 확인
        Job job = jobRepository.findById(jobNo)
                .orElseThrow(() -> new UserException("분석결과 정보를 찾을 수 없습니다."));
        
        List<AnalysisResultInfoResponseDto> resultList = new ArrayList<>();
        
        // 2) 타겟 회사 정보 조회 (Job 테이블의 analysisResultNo)
        Long targetAnalysisResultNo = job.getAnalysisResultNo();
        AnalysisResult targetAnalysisResult = analysisResultRepository
                .findByAnalysisResultNoWithCompanyAndIndustry(targetAnalysisResultNo)
                .orElseThrow(() -> new UserException("타겟 회사 분석결과 정보를 찾을 수 없습니다."));
        
        // 타겟 회사 DTO 생성
        AnalysisResultInfoResponseDto targetDto = new AnalysisResultInfoResponseDto(
                targetAnalysisResult.getCompanyNo(),
                targetAnalysisResult.getCompany().getCompanyName(),
                targetAnalysisResultNo,
                "Y" // 타겟 회사
        );
        resultList.add(targetDto);
        
        // 3) 경쟁 회사들 정보 조회 (AnalysisResultJob 테이블의 analysisResultNo들)
        List<AnalysisResultJob> analysisResultJobs = analysisResultJobRepository.findByJobNo(jobNo);
        
        for (AnalysisResultJob arJob : analysisResultJobs) {
            Long competitorAnalysisResultNo = arJob.getAnalysisResultNo();
            
            // 타겟 회사와 동일한 analysisResultNo는 제외
            if (!competitorAnalysisResultNo.equals(targetAnalysisResultNo)) {
                AnalysisResult competitorAnalysisResult = analysisResultRepository
                        .findByAnalysisResultNoWithCompanyAndIndustry(competitorAnalysisResultNo)
                        .orElse(null);
                
                if (competitorAnalysisResult != null) {
                    AnalysisResultInfoResponseDto competitorDto = new AnalysisResultInfoResponseDto(
                            competitorAnalysisResult.getCompanyNo(),
                            competitorAnalysisResult.getCompany().getCompanyName(),
                            competitorAnalysisResultNo,
                            "N" // 경쟁 회사
                    );
                    resultList.add(competitorDto);
                }
            }
        }
        
        // 4) 타겟 회사가 맨 앞에 오도록 정렬 (이미 타겟 회사를 먼저 추가했지만 명시적으로 정렬)
        resultList.sort((a, b) -> {
            if ("Y".equals(a.getTargetCompanyYn()) && "N".equals(b.getTargetCompanyYn())) {
                return -1;
            } else if ("N".equals(a.getTargetCompanyYn()) && "Y".equals(b.getTargetCompanyYn())) {
                return 1;
            } else {
                return a.getCompanyName().compareTo(b.getCompanyName());
            }
        });
        
        return resultList;
    }

}
