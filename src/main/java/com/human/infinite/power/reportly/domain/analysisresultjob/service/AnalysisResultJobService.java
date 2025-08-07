package com.human.infinite.power.reportly.domain.analysisresultjob.service;

import com.human.infinite.power.reportly.domain.analysisresultjob.repository.AnalysisResultJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 분석 결과-업무 매핑 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalysisResultJobService {
    
    private final AnalysisResultJobRepository analysisResultJobRepository;
    
    // TODO: 비즈니스 로직 메서드들을 추가할 예정
}
