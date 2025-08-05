package com.human.infinite.power.reportly.domain.competitor.repository;

import com.human.infinite.power.reportly.domain.competitor.entity.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 경쟁사 정보 Repository
 */
@Repository
public interface CompetitorRepository extends JpaRepository<Competitor, Long> {
    
    /**
     * 분석결과 번호로 경쟁사 목록을 조회합니다.
     * 
     * @param analysisResultNo 분석결과 번호
     * @return 경쟁사 목록
     */
    @Query("SELECT c FROM Competitor c " +
           "JOIN FETCH c.company " +
           "WHERE c.analysisResultNo = :analysisResultNo")
    List<Competitor> findByAnalysisResultNoWithCompany(@Param("analysisResultNo") Long analysisResultNo);
    
    /**
     * 분석결과 번호로 경쟁사 회사 번호 목록을 조회합니다.
     * 
     * @param analysisResultNo 분석결과 번호
     * @return 회사 번호 목록
     */
    @Query("SELECT c.companyNo FROM Competitor c WHERE c.analysisResultNo = :analysisResultNo")
    List<Long> findCompanyNoListByAnalysisResultNo(@Param("analysisResultNo") Long analysisResultNo);
}
