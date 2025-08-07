package com.human.infinite.power.reportly.domain.analysisresultscore.repository;

import com.human.infinite.power.reportly.domain.analysisresultscore.entity.AnalysisResultScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 분석결과 점수 Repository
 */
@Repository
public interface AnalysisResultScoreRepository extends JpaRepository<AnalysisResultScore, Long> {

    List<AnalysisResultScore> findByAnalysisResultNo(Long analysisResultNo);

    List<AnalysisResultScore> findAllByAnalysisResultNoIn(List<Long> analysisResultNos);
    
    /**
     * 분석결과 번호로 카테고리별 점수를 카테고리와 함께 조회합니다.
     * 
     * @param analysisResultNo 분석결과 번호
     * @return 분석결과 점수 목록
     */
    @Query("SELECT ars FROM AnalysisResultScore ars " +
           "JOIN FETCH ars.category " +
           "WHERE ars.analysisResultNo = :analysisResultNo " +
           "ORDER BY ars.category.categoryName ASC")
    List<AnalysisResultScore> findByAnalysisResultNoWithCategory(@Param("analysisResultNo") Long analysisResultNo);
    
    /**
     * 분석결과 번호 목록으로 카테고리별 평균 점수를 계산합니다.
     * 
     * @param analysisResultNoList 분석결과 번호 목록
     * @return 카테고리별 평균 점수 목록
     */
    @Query("SELECT ars.categoryNo, c.categoryName, AVG(ars.categoryScore) " +
           "FROM AnalysisResultScore ars " +
           "JOIN ars.category c " +
           "WHERE ars.analysisResultNo IN :analysisResultNoList " +
           "GROUP BY ars.categoryNo, c.categoryName " +
           "ORDER BY c.categoryName ASC")
    List<Object[]> findAvgScoreByAnalysisResultNoList(@Param("analysisResultNoList") List<Long> analysisResultNoList);
    
    /**
     * 특정 분석결과와 회사의 카테고리별 점수를 조회합니다.
     * 
     * @param analysisResultNo 분석결과 번호
     * @param companyNo 회사 번호
     * @return 카테고리별 점수 목록
     */
    @Query("SELECT ars FROM AnalysisResultScore ars " +
           "JOIN FETCH ars.category " +
           "JOIN ars.analysisResult ar " +
           "WHERE ars.analysisResultNo = :analysisResultNo " +
           "AND ar.companyNo = :companyNo " +
           "ORDER BY ars.category.categoryName ASC")
    List<AnalysisResultScore> findByAnalysisResultNoAndCompanyNoWithCategory(@Param("analysisResultNo") Long analysisResultNo, 
                                                                            @Param("companyNo") Long companyNo);
}
