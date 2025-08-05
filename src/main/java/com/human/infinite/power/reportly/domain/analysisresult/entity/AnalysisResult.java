package com.human.infinite.power.reportly.domain.analysisresult.entity;

import com.human.infinite.power.reportly.common.IdGenerator;
import com.human.infinite.power.reportly.domain.company.entity.Company;
import com.human.infinite.power.reportly.domain.industry.entity.Industry;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 분석 결과 정보를 관리하는 Entity 클래스
 */
@Entity
@Table(name = "AnalysisResult",
       uniqueConstraints = {
           @UniqueConstraint(
               name = "UQ_ANALYSISRESULT_COMP_IND_DATE",
               columnNames = {"companyNo", "industryNo", "date"}
           )
       })
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnalysisResult {
    
    /**
     * 분석 결과 번호 (Primary Key)
     */
    @Id
    @Column(name = "analysisResultNo")
    private Long analysisResultNo;
    
    /**
     * 회사 번호 (FK → Company.companyNo)
     */
    @Column(name = "companyNo", nullable = false)
    private Long companyNo;
    
    /**
     * 업종 번호 (FK → Industry.industryNo)
     */
    @Column(name = "industryNo", nullable = false)
    private Long industryNo;
    
    /**
     * 분석 일자
     */
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    /**
     * 요약
     */
    @Column(name = "summary", nullable = false)
    private String summary;
    
    /**
     * 상세 내용
     */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    /**
     * 회사·업종 종합 점수 (반정규화 허용)
     */
    @Column(name = "companyIndustryTotalScore", nullable = false)
    private Double companyIndustryTotalScore;
    
    /**
     * 근거 자료
     */
    @Column(name = "supporting_evidence", nullable = false)
    private String supportingEvidence;
    
    /**
     * 강점
     */
    @Column(name = "strongPoint", nullable = false, columnDefinition = "TEXT")
    private String strongPoint;
    
    /**
     * 약점
     */
    @Column(name = "weakPoint", nullable = false, columnDefinition = "TEXT")
    private String weakPoint;
    
    /**
     * 개선 사항
     */
    @Column(name = "improvements", nullable = false, columnDefinition = "TEXT")
    private String improvements;
    
    /**
     * 생성 일시
     */
    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 수정 일시
     */
    @UpdateTimestamp
    @Column(name = "modifiedAt", nullable = false)
    private LocalDateTime modifiedAt;
    
    /**
     * 비고
     */
    @Column(name = "note")
    private String note;
    
    /**
     * Company와의 연관관계 (fetch join 사용을 위한 매핑)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyNo", insertable = false, updatable = false)
    private Company company;
    
    /**
     * Industry와의 연관관계 (fetch join 사용을 위한 매핑)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industryNo", insertable = false, updatable = false)
    private Industry industry;
    
    /**
     * AnalysisResult 생성자
     * 
     * @param companyNo 회사 번호
     * @param industryNo 업종 번호
     * @param date 분석 일자
     * @param summary 요약
     * @param content 상세 내용
     * @param companyIndustryTotalScore 종합 점수
     * @param supportingEvidence 근거 자료
     * @param strongPoint 강점
     * @param weakPoint 약점
     * @param improvements 개선 사항
     */
    public AnalysisResult(Long companyNo, Long industryNo, LocalDate date, String summary, 
                         String content, Double companyIndustryTotalScore, String supportingEvidence,
                         String strongPoint, String weakPoint, String improvements) {
        this.analysisResultNo = IdGenerator.generateId();
        this.companyNo = companyNo;
        this.industryNo = industryNo;
        this.date = date;
        this.summary = summary;
        this.content = content;
        this.companyIndustryTotalScore = companyIndustryTotalScore;
        this.supportingEvidence = supportingEvidence;
        this.strongPoint = strongPoint;
        this.weakPoint = weakPoint;
        this.improvements = improvements;
    }
}
