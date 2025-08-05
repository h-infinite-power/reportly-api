package com.human.infinite.power.reportly.domain.industry.repository;

import com.human.infinite.power.reportly.domain.industry.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 업종 정보 Repository
 */
@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {
    
    /**
     * 모든 업종 목록을 업종 이름 순으로 조회합니다.
     * 
     * @return 업종 목록
     */
    @Query("SELECT i FROM Industry i ORDER BY i.industryName ASC")
    List<Industry> findAllOrderByIndustryName();
    
    /**
     * 업종 번호로 업종 존재 여부를 확인합니다.
     * 
     * @param industryNo 업종 번호
     * @return 존재 여부
     */
    boolean existsByIndustryNo(Long industryNo);
}
