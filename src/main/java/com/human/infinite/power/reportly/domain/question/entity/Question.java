package com.human.infinite.power.reportly.domain.question.entity;

import com.human.infinite.power.reportly.common.IdGenerator;
import com.human.infinite.power.reportly.domain.category.entity.Category;
import com.human.infinite.power.reportly.domain.industry.entity.Industry;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 질문 정보를 관리하는 Entity 클래스
 */
@Entity
@Table(name = "Question")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    
    /**
     * 질문 번호 (Primary Key)
     */
    @Id
    @Column(name = "questionNo")
    private Long questionNo;
    
    /**
     * 업종 번호 (FK → Industry.industryNo)
     */
    @Column(name = "industryNo", nullable = false)
    private Long industryNo;
    
    /**
     * 카테고리 번호 (FK → Category.categoryNo)
     */
    @Column(name = "categoryNo", nullable = false)
    private Long categoryNo;
    
    /**
     * 질문 내용
     */
    @Column(name = "question", nullable = false)
    private String question;
    
    /**
     * 정렬 순서
     */
    @Column(name = "orderNo", nullable = false)
    private Integer orderNo;
    
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
     * Industry와의 연관관계 (fetch join 사용을 위한 매핑)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industryNo", insertable = false, updatable = false)
    private Industry industry;
    
    /**
     * Category와의 연관관계 (fetch join 사용을 위한 매핑)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryNo", insertable = false, updatable = false)
    private Category category;
    
    /**
     * Question 생성자
     * 
     * @param industryNo 업종 번호
     * @param categoryNo 카테고리 번호
     * @param question 질문 내용
     * @param orderNo 정렬 순서
     */
    public Question(Long industryNo, Long categoryNo, String question, Integer orderNo) {
        this.questionNo = IdGenerator.generateId();
        this.industryNo = industryNo;
        this.categoryNo = categoryNo;
        this.question = question;
        this.orderNo = orderNo;
    }
    
    /**
     * Question 생성자 (비고 포함)
     * 
     * @param industryNo 업종 번호
     * @param categoryNo 카테고리 번호
     * @param question 질문 내용
     * @param orderNo 정렬 순서
     * @param note 비고
     */
    public Question(Long industryNo, Long categoryNo, String question, Integer orderNo, String note) {
        this.questionNo = IdGenerator.generateId();
        this.industryNo = industryNo;
        this.categoryNo = categoryNo;
        this.question = question;
        this.orderNo = orderNo;
        this.note = note;
    }
}
