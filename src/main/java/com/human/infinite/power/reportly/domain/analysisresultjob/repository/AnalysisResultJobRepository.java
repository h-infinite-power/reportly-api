package com.human.infinite.power.reportly.domain.analysisresultjob.repository;

import com.human.infinite.power.reportly.domain.analysisresultjob.entity.AnalysisResultJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 분석 결과-업무 매핑 정보 Repository
 */
@Repository
public interface AnalysisResultJobRepository extends JpaRepository<AnalysisResultJob, Long> {
    
    /**
     * 업무 번호로 분석 결과-업무 매핑 목록을 조회합니다.
     * 
     * @param jobNo 업무 번호
     * @return 분석 결과-업무 매핑 목록
     */
    List<AnalysisResultJob> findByJobNo(Long jobNo);
    
    /**
     * 분석 결과 번호로 분석 결과-업무 매핑 목록을 조회합니다.
     * 
     * @param analysisResultNo 분석 결과 번호
     * @return 분석 결과-업무 매핑 목록
     */
    List<AnalysisResultJob> findByAnalysisResultNo(Long analysisResultNo);
    
    /**
     * 업무 번호로 분석 결과-업무 매핑을 Job과 AnalysisResult와 함께 조회합니다.
     * 
     * @param jobNo 업무 번호
     * @return 분석 결과-업무 매핑 목록
     */
    @Query("SELECT arj FROM AnalysisResultJob arj " +
           "JOIN FETCH arj.job " +
           "JOIN FETCH arj.analysisResult " +
           "WHERE arj.jobNo = :jobNo")
    List<AnalysisResultJob> findByJobNoWithJobAndAnalysisResult(@Param("jobNo") Long jobNo);
    
    /**
     * 분석 결과 번호로 분석 결과-업무 매핑을 Job과 AnalysisResult와 함께 조회합니다.
     * 
     * @param analysisResultNo 분석 결과 번호
     * @return 분석 결과-업무 매핑 목록
     */
    @Query("SELECT arj FROM AnalysisResultJob arj " +
           "JOIN FETCH arj.job " +
           "JOIN FETCH arj.analysisResult " +
           "WHERE arj.analysisResultNo = :analysisResultNo")
    List<AnalysisResultJob> findByAnalysisResultNoWithJobAndAnalysisResult(@Param("analysisResultNo") Long analysisResultNo);
    
    /**
     * 업무 번호와 분석 결과 번호로 분석 결과-업무 매핑의 존재 여부를 확인합니다.
     * 
     * @param jobNo 업무 번호
     * @param analysisResultNo 분석 결과 번호
     * @return 존재 여부
     */
    boolean existsByJobNoAndAnalysisResultNo(Long jobNo, Long analysisResultNo);
    
    /**
     * 업무 번호로 관련된 분석 결과 번호 목록을 조회합니다.
     * 
     * @param jobNo 업무 번호
     * @return 분석 결과 번호 목록
     */
    @Query("SELECT arj.analysisResultNo FROM AnalysisResultJob arj WHERE arj.jobNo = :jobNo")
    List<Long> findAnalysisResultNoListByJobNo(@Param("jobNo") Long jobNo);
    
    /**
     * 분석 결과 번호로 관련된 업무 번호 목록을 조회합니다.
     * 
     * @param analysisResultNo 분석 결과 번호
     * @return 업무 번호 목록
     */
    @Query("SELECT arj.jobNo FROM AnalysisResultJob arj WHERE arj.analysisResultNo = :analysisResultNo")
    List<Long> findJobNoListByAnalysisResultNo(@Param("analysisResultNo") Long analysisResultNo);
}
