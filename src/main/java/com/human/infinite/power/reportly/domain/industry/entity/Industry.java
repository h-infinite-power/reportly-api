package com.human.infinite.power.reportly.domain.industry.entity;

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
 * 업종 정보를 관리하는 Entity 클래스
 */
@Entity
@Table(name = "Industry")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Industry {
    
    /**
     * 업종 번호 (Primary Key)
     */
    @Id
    @Column(name = "industryNo")
    private Long industryNo;
    
    /**
     * 업종 이름
     */
    @Column(name = "industryName", nullable = false)
    private String industryName;
    
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
     * Industry 생성자
     * 
     * @param industryName 업종 이름
     */
    public Industry(String industryName) {
        this.industryNo = IdGenerator.generateId();
        this.industryName = industryName;
    }
    
    /**
     * Industry 생성자 (비고 포함)
     * 
     * @param industryName 업종 이름
     * @param note 비고
     */
    public Industry(String industryName, String note) {
        this.industryNo = IdGenerator.generateId();
        this.industryName = industryName;
        this.note = note;
    }
}
