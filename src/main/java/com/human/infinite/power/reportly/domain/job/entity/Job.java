package com.human.infinite.power.reportly.domain.job.entity;

import com.human.infinite.power.reportly.common.IdGenerator;
import com.human.infinite.power.reportly.domain.analysisresult.entity.AnalysisResult;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 업무 정보를 관리하는 Entity 클래스
 */
@Entity
@Table(name = "Job")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Job {
    
    /**
     * 업무 번호 (Primary Key)
     */
    @Id
    @Column(name = "jobNo")
    private Long jobNo;
    
    /**
     * 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
     */
    @Column(name = "analysisResultNo", nullable = false)
    private Long analysisResultNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysisResultNo", insertable = false, updatable = false)
    private AnalysisResult analysisResult;
    
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
     * Job 생성자
     * 
     * @param analysisResultNo 분석 결과 번호
     */
    public Job(Long analysisResultNo) {
        this.jobNo = IdGenerator.generateId();
        this.analysisResultNo = analysisResultNo;
    }
    
    /**
     * Job 생성자 (비고 포함)
     * 
     * @param analysisResultNo 분석 결과 번호
     * @param note 비고
     */
    public Job(Long analysisResultNo, String note) {
        this.jobNo = IdGenerator.generateId();
        this.analysisResultNo = analysisResultNo;
        this.note = note;
    }
}
