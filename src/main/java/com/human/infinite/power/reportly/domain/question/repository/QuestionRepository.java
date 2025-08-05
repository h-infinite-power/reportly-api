package com.human.infinite.power.reportly.domain.question.repository;

import com.human.infinite.power.reportly.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 질문 정보 Repository
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    /**
     * 업종번호로 질문 목록을 카테고리와 함께 조회합니다.
     * 
     * @param industryNo 업종 번호
     * @return 질문 목록
     */
    @Query("SELECT q FROM Question q " +
           "JOIN FETCH q.category " +
           "WHERE q.industryNo = :industryNo " +
           "ORDER BY q.orderNo ASC")
    List<Question> findByIndustryNoWithCategory(@Param("industryNo") Long industryNo);
    
    /**
     * 업종번호와 카테고리번호로 질문 목록을 조회합니다.
     * 
     * @param industryNo 업종 번호
     * @param categoryNo 카테고리 번호
     * @return 질문 목록
     */
    @Query("SELECT q FROM Question q " +
           "WHERE q.industryNo = :industryNo " +
           "AND q.categoryNo = :categoryNo " +
           "ORDER BY q.orderNo ASC")
    List<Question> findByIndustryNoAndCategoryNo(@Param("industryNo") Long industryNo, 
                                                @Param("categoryNo") Long categoryNo);
}
