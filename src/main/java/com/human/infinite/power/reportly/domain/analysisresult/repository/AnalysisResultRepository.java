package com.human.infinite.power.reportly.domain.analysisresult.repository;

import com.human.infinite.power.reportly.domain.analysisresult.entity.AnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 분석 결과 Repository
 */
@Repository
public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, Long> {
    
    /**
     * 회사번호, 업종번호, 날짜로 분석결과 존재 여부를 확인합니다.
     * 
     * @param companyNo 회사 번호
     * @param industryNo 업종 번호
     * @param date 분석 일자
     * @return 존재 여부
     */
    boolean existsByCompanyNoAndIndustryNoAndDate(Long companyNo, Long industryNo, LocalDate date);
    
    /**
     * 회사번호, 업종번호로 최신순으로 분석결과를 조회합니다.
     * 
     * @param companyNo 회사 번호
     * @param industryNo 업종 번호
     * @return 분석결과
     */
    List<AnalysisResult> findByCompanyNoAndIndustryNoOrderByCreatedAtDesc(Long companyNo, Long industryNo);

    /**
     * 업종번호와 날짜로 분석결과 목록을 조회합니다.
     * @param industryNo 업종 번호
     * @param date 분석 일자
     * @return 분석결과 목록
     */
    List<AnalysisResult> findByIndustryNoAndDate(Long industryNo, java.time.LocalDate date);
    
    /**
     * 분석결과번호로 회사와 업종 정보를 함께 조회합니다.
     * 
     * @param analysisResultNo 분석결과 번호
     * @return 분석결과
     */
    @Query("SELECT ar FROM AnalysisResult ar " +
           "JOIN FETCH ar.company " +
           "JOIN FETCH ar.industry " +
           "WHERE ar.analysisResultNo = :analysisResultNo")
    Optional<AnalysisResult> findByAnalysisResultNoWithCompanyAndIndustry(@Param("analysisResultNo") Long analysisResultNo);
    
    /**
     * 업종번호와 날짜로 해당 업종의 모든 분석결과를 조회합니다.
     * 
     * @param industryNo 업종 번호
     * @param date 분석 일자
     * @return 분석결과 목록
     */
    @Query("SELECT ar FROM AnalysisResult ar " +
           "WHERE ar.industryNo = :industryNo AND ar.date = :date " +
           "ORDER BY ar.companyIndustryTotalScore DESC")
    List<AnalysisResult> findByIndustryNoAndDateOrderByTotalScoreDesc(@Param("industryNo") Long industryNo, 
                                                                      @Param("date") LocalDate date);
    
    /**
     * 여러 회사번호, 업종번호, 날짜로 분석결과를 조회합니다.
     * 
     * @param companyNos 회사 번호 목록
     * @param industryNo 업종 번호
     * @param date 분석 일자
     * @return 분석결과 목록
     */
    @Query("SELECT ar FROM AnalysisResult ar " +
           "WHERE ar.companyNo IN :companyNos AND ar.industryNo = :industryNo AND ar.date = :date")
    List<AnalysisResult> findByCompanyNoInAndIndustryNoAndDate(@Param("companyNos") List<Long> companyNos, 
                                                               @Param("industryNo") Long industryNo, 
                                                               @Param("date") LocalDate date);
}
