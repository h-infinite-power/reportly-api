package com.human.infinite.power.reportly.common.dto;

/**
 * 이름만 받는 공통 요청 DTO
 */
public class NameRequestDto {
    
    /**
     * 이름
     */
    private String name;
    
    /**
     * 기본 생성자
     */
    public NameRequestDto() {
    }
    
    /**
     * 생성자
     * 
     * @param name 이름
     */
    public NameRequestDto(String name) {
        this.name = name;
    }
    
    // Getter and Setter
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
