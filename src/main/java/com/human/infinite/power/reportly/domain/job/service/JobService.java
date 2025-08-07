package com.human.infinite.power.reportly.domain.job.service;

import com.human.infinite.power.reportly.domain.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 업무 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobService {
    
    private final JobRepository jobRepository;
    
    // TODO: 비즈니스 로직 메서드들을 추가할 예정
}
