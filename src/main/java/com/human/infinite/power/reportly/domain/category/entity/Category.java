package com.human.infinite.power.reportly.domain.category.entity;

import com.human.infinite.power.reportly.common.IdGenerator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 카테고리 정보를 관리하는 Entity 클래스
 */
@Entity
@Table(name = "Category")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    
    /**
     * 카테고리 번호 (Primary Key)
     */
    @Id
    @Column(name = "categoryNo")
    private Long categoryNo;
    
    /**
     * 카테고리 이름
     */
    @Column(name = "categoryName", nullable = false)
    private String categoryName;
    
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
     * Category 생성자
     * 
     * @param categoryName 카테고리 이름
     */
    public Category(String categoryName) {
        this.categoryNo = IdGenerator.generateId();
        this.categoryName = categoryName;
    }
    
    /**
     * Category 생성자 (비고 포함)
     * 
     * @param categoryName 카테고리 이름
     * @param note 비고
     */
    public Category(String categoryName, String note) {
        this.categoryNo = IdGenerator.generateId();
        this.categoryName = categoryName;
        this.note = note;
    }
}
