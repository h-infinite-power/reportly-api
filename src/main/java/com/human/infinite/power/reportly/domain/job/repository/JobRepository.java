package com.human.infinite.power.reportly.domain.job.repository;

import com.human.infinite.power.reportly.domain.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 업무 정보 Repository
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    
    /**
     * 분석 결과 번호로 업무를 조회합니다.
     * 
     * @param analysisResultNo 분석 결과 번호
     * @return 업무 정보
     */
    Optional<Job> findByAnalysisResultNo(Long analysisResultNo);
    
    /**
     * 분석 결과 번호로 업무 목록을 조회합니다.
     * 
     * @param analysisResultNo 분석 결과 번호
     * @return 업무 목록
     */
    List<Job> findAllByAnalysisResultNo(Long analysisResultNo);
    
    /**
     * 분석 결과 번호가 포함된 업무 목록을 조회합니다.
     * 
     * @param analysisResultNoList 분석 결과 번호 목록
     * @return 업무 목록
     */
    @Query("SELECT j FROM Job j WHERE j.analysisResultNo IN :analysisResultNoList")
    List<Job> findByAnalysisResultNoIn(@Param("analysisResultNoList") List<Long> analysisResultNoList);
    
    /**
     * 업무 번호로 업무 존재 여부를 확인합니다.
     * 
     * @param jobNo 업무 번호
     * @return 존재 여부
     */
    boolean existsByJobNo(Long jobNo);
}
