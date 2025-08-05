package com.human.infinite.power.reportly.domain.analysisresultscore.entity;

import com.human.infinite.power.reportly.common.IdGenerator;
import com.human.infinite.power.reportly.domain.analysisresult.entity.AnalysisResult;
import com.human.infinite.power.reportly.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 분석 결과 점수 정보를 관리하는 Entity 클래스
 */
@Entity
@Table(name = "AnalysisResultScore")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnalysisResultScore {
    
    /**
     * 분석 결과 점수 번호 (Primary Key)
     */
    @Id
    @Column(name = "analysisResultScoreNo")
    private Long analysisResultScoreNo;
    
    /**
     * 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
     */
    @Column(name = "analysisResultNo", nullable = false)
    private Long analysisResultNo;
    
    /**
     * 카테고리 번호 (FK → Category.categoryNo)
     */
    @Column(name = "categoryNo", nullable = false)
    private Long categoryNo;
    
    /**
     * 카테고리별 점수
     */
    @Column(name = "categoryScore", nullable = false)
    private Float categoryScore;
    
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
     * Category와의 연관관계 (fetch join 사용을 위한 매핑)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryNo", insertable = false, updatable = false)
    private Category category;
    
    /**
     * AnalysisResultScore 생성자
     * 
     * @param analysisResultNo 분석 결과 번호
     * @param categoryNo 카테고리 번호
     * @param categoryScore 카테고리별 점수
     */
    public AnalysisResultScore(Long analysisResultNo, Long categoryNo, Float categoryScore) {
        this.analysisResultScoreNo = IdGenerator.generateId();
        this.analysisResultNo = analysisResultNo;
        this.categoryNo = categoryNo;
        this.categoryScore = categoryScore;
    }
    
    /**
     * AnalysisResultScore 생성자 (비고 포함)
     * 
     * @param analysisResultNo 분석 결과 번호
     * @param categoryNo 카테고리 번호
     * @param categoryScore 카테고리별 점수
     * @param note 비고
     */
    public AnalysisResultScore(Long analysisResultNo, Long categoryNo, Float categoryScore, String note) {
        this.analysisResultScoreNo = IdGenerator.generateId();
        this.analysisResultNo = analysisResultNo;
        this.categoryNo = categoryNo;
        this.categoryScore = categoryScore;
        this.note = note;
    }
}
