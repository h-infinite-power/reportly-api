package com.human.infinite.power.reportly.domain.company.entity;

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
 * 회사 정보를 관리하는 Entity 클래스
 */
@Entity
@Table(name = "Company")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {
    
    /**
     * 회사 번호 (Primary Key)
     */
    @Id
    @Column(name = "companyNo")
    private Long companyNo;
    
    /**
     * 회사 이름
     */
    @Column(name = "companyName", nullable = false)
    private String companyName;
    
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
     * Company 생성자
     * 
     * @param companyName 회사 이름
     */
    public Company(String companyName) {
        this.companyNo = IdGenerator.generateId();
        this.companyName = companyName;
    }
    
    /**
     * Company 생성자 (비고 포함)
     * 
     * @param companyName 회사 이름
     * @param note 비고
     */
    public Company(String companyName, String note) {
        this.companyNo = IdGenerator.generateId();
        this.companyName = companyName;
        this.note = note;
    }
}
