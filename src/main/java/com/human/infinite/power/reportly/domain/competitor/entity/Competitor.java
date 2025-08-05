package com.human.infinite.power.reportly.domain.competitor.entity;

import com.human.infinite.power.reportly.common.IdGenerator;
import com.human.infinite.power.reportly.domain.analysisresult.entity.AnalysisResult;
import com.human.infinite.power.reportly.domain.company.entity.Company;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 경쟁사 정보를 관리하는 Entity 클래스
 */
@Entity
@Table(name = "Competitor")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Competitor {
    
    /**
     * 경쟁사 번호 (Primary Key)
     */
    @Id
    @Column(name = "competitorNo")
    private Long competitorNo;
    
    /**
     * 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
     */
    @Column(name = "analysisResultNo", nullable = false)
    private Long analysisResultNo;
    
    /**
     * 회사 번호 (FK → Company.companyNo)
     */
    @Column(name = "companyNo", nullable = false)
    private Long companyNo;
    
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
     * AnalysisResult와의 연관관계 (fetch join 사용을 위한 매핑)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysisResultNo", insertable = false, updatable = false)
    private AnalysisResult analysisResult;
    
    /**
     * Company와의 연관관계 (fetch join 사용을 위한 매핑)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyNo", insertable = false, updatable = false)
    private Company company;
    
    /**
     * Competitor 생성자
     * 
     * @param analysisResultNo 분석 결과 번호
     * @param companyNo 회사 번호
     */
    public Competitor(Long analysisResultNo, Long companyNo) {
        this.competitorNo = IdGenerator.generateId();
        this.analysisResultNo = analysisResultNo;
        this.companyNo = companyNo;
    }
    
    /**
     * Competitor 생성자 (비고 포함)
     * 
     * @param analysisResultNo 분석 결과 번호
     * @param companyNo 회사 번호
     * @param note 비고
     */
    public Competitor(Long analysisResultNo, Long companyNo, String note) {
        this.competitorNo = IdGenerator.generateId();
        this.analysisResultNo = analysisResultNo;
        this.companyNo = companyNo;
        this.note = note;
    }
}
