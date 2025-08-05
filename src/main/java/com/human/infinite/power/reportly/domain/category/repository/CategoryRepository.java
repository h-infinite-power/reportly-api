package com.human.infinite.power.reportly.domain.category.repository;

import com.human.infinite.power.reportly.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 카테고리 정보 Repository
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * 모든 카테고리 목록을 카테고리 이름 순으로 조회합니다.
     * 
     * @return 카테고리 목록
     */
    @Query("SELECT c FROM Category c ORDER BY c.categoryName ASC")
    List<Category> findAllOrderByCategoryName();
    
    /**
     * 카테고리 번호로 카테고리 존재 여부를 확인합니다.
     * 
     * @param categoryNo 카테고리 번호
     * @return 존재 여부
     */
    boolean existsByCategoryNo(Long categoryNo);
}
