package com.human.infinite.power.reportly.domain.company.repository;

import com.human.infinite.power.reportly.domain.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 회사 정보 Repository
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    
    /**
     * 모든 회사 목록을 회사 이름 순으로 조회합니다.
     * 
     * @return 회사 목록
     */
    @Query("SELECT c FROM Company c ORDER BY c.companyName ASC")
    List<Company> findAllOrderByCompanyName();
    
    /**
     * 회사 번호로 회사 존재 여부를 확인합니다.
     * 
     * @param companyNo 회사 번호
     * @return 존재 여부
     */
    boolean existsByCompanyNo(Long companyNo);
    
    /**
     * 회사 번호 목록으로 회사들을 조회합니다.
     * 
     * @param companyNoList 회사 번호 목록
     * @return 회사 목록
     */
    @Query("SELECT c FROM Company c WHERE c.companyNo IN :companyNoList")
    List<Company> findByCompanyNoIn(List<Long> companyNoList);
}
