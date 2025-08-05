package com.human.infinite.power.reportly.domain.keyword.repository;

import com.human.infinite.power.reportly.domain.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 키워드 정보 Repository
 */
@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    
    /**
     * 분석결과 번호로 키워드 목록을 조회합니다.
     * 
     * @param analysisResultNo 분석결과 번호
     * @return 키워드 목록
     */
    List<Keyword> findByAnalysisResultNo(Long analysisResultNo);
    
    /**
     * 분석결과 번호와 키워드 유형으로 키워드 목록을 조회합니다.
     * 
     * @param analysisResultNo 분석결과 번호
     * @param type 키워드 유형
     * @return 키워드 목록
     */
    @Query("SELECT k FROM Keyword k " +
           "WHERE k.analysisResultNo = :analysisResultNo " +
           "AND k.type = :type " +
           "ORDER BY k.createdAt ASC")
    List<Keyword> findByAnalysisResultNoAndType(@Param("analysisResultNo") Long analysisResultNo, 
                                               @Param("type") String type);
}
