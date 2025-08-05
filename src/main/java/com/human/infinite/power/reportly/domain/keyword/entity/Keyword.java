package com.human.infinite.power.reportly.domain.keyword.entity;

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
 * 키워드 정보를 관리하는 Entity 클래스
 */
@Entity
@Table(name = "Keyword")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {
    
    /**
     * 키워드 번호 (Primary Key)
     */
    @Id
    @Column(name = "keywordNo")
    private Long keywordNo;
    
    /**
     * 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
     */
    @Column(name = "analysisResultNo", nullable = false)
    private Long analysisResultNo;
    
    /**
     * 키워드 유형
     */
    @Column(name = "type")
    private String type;
    
    /**
     * 키워드 내용
     */
    @Column(name = "content", nullable = false)
    private String content;
    
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
     * Keyword 생성자
     * 
     * @param analysisResultNo 분석 결과 번호
     * @param content 키워드 내용
     */
    public Keyword(Long analysisResultNo, String content) {
        this.keywordNo = IdGenerator.generateId();
        this.analysisResultNo = analysisResultNo;
        this.content = content;
    }
    
    /**
     * Keyword 생성자 (유형 포함)
     * 
     * @param analysisResultNo 분석 결과 번호
     * @param type 키워드 유형
     * @param content 키워드 내용
     */
    public Keyword(Long analysisResultNo, String type, String content) {
        this.keywordNo = IdGenerator.generateId();
        this.analysisResultNo = analysisResultNo;
        this.type = type;
        this.content = content;
    }
    
    /**
     * Keyword 생성자 (모든 필드 포함)
     * 
     * @param analysisResultNo 분석 결과 번호
     * @param type 키워드 유형
     * @param content 키워드 내용
     * @param note 비고
     */
    public Keyword(Long analysisResultNo, String type, String content, String note) {
        this.keywordNo = IdGenerator.generateId();
        this.analysisResultNo = analysisResultNo;
        this.type = type;
        this.content = content;
        this.note = note;
    }
}
