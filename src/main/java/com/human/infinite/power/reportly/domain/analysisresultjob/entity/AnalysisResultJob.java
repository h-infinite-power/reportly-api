package com.human.infinite.power.reportly.domain.analysisresultjob.entity;

import com.human.infinite.power.reportly.common.IdGenerator;
import com.human.infinite.power.reportly.domain.analysisresult.entity.AnalysisResult;
import com.human.infinite.power.reportly.domain.job.entity.Job;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 분석 결과-업무 매핑 정보를 관리하는 Entity 클래스
 */
@Entity
@Table(name = "AnalysisResultJob")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnalysisResultJob {
    
    /**
     * 분석 결과-업무 매핑 번호 (Primary Key)
     */
    @Id
    @Column(name = "analysisResultJobNo")
    private Long analysisResultJobNo;
    
    /**
     * 업무 번호 (FK → Job.jobNo)
     */
    @Column(name = "jobNo", nullable = false)
    private Long jobNo;
    
    /**
     * 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
     */
    @Column(name = "analysisResultNo", nullable = false)
    private Long analysisResultNo;
    
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
     * Job과의 연관관계 (fetch join 사용을 위한 매핑)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobNo", insertable = false, updatable = false)
    private Job job;
    
    /**
     * AnalysisResult와의 연관관계 (fetch join 사용을 위한 매핑)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysisResultNo", insertable = false, updatable = false)
    private AnalysisResult analysisResult;
    
    /**
     * AnalysisResultJob 생성자
     * 
     * @param jobNo 업무 번호
     * @param analysisResultNo 분석 결과 번호
     */
    public AnalysisResultJob(Long jobNo, Long analysisResultNo) {
        this.analysisResultJobNo = IdGenerator.generateId();
        this.jobNo = jobNo;
        this.analysisResultNo = analysisResultNo;
    }
    
    /**
     * AnalysisResultJob 생성자 (비고 포함)
     * 
     * @param jobNo 업무 번호
     * @param analysisResultNo 분석 결과 번호
     * @param note 비고
     */
    public AnalysisResultJob(Long jobNo, Long analysisResultNo, String note) {
        this.analysisResultJobNo = IdGenerator.generateId();
        this.jobNo = jobNo;
        this.analysisResultNo = analysisResultNo;
        this.note = note;
    }
}
