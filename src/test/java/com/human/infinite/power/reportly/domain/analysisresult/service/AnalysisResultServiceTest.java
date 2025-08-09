package com.human.infinite.power.reportly.domain.analysisresult.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.human.infinite.power.reportly.domain.analysisresult.dto.prompt.PromptResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AnalysisResultService 테스트 클래스
 * JSON 파일을 읽어와서 PromptResponseDto로 역직렬화하고 데이터 저장을 테스트합니다.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AnalysisResultServiceTest {

    @Autowired
    private AnalysisResultService analysisResultService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * JSON 파일을 읽어와서 List<PromptResponseDto>로 역직렬화하고
     * saveAnalysisResultFromPrompt 메서드를 테스트합니다.
     */
    @Test
    void testJsonDeserializationAndDataSaving() throws Exception {
        // given
        List<Long> companyNoList = List.of(1483912791016180743L, 1483912911038656997L, 1483913055452341553L, 1483912956654371967L); // Toss, 삼성금융, KB국민은행, 카카오페이
        Long industryNo = 1483912168854887831L; // 금융/핀테크
        
        // JSON 파일 읽기
        ClassPathResource resource = new ClassPathResource("prompt-responses.json");
        InputStream inputStream = resource.getInputStream();
        
        // JSON을 List<PromptResponseDto>로 역직렬화
        List<PromptResponseDto> promptResponses = objectMapper.readValue(
            inputStream, 
            new TypeReference<List<PromptResponseDto>>() {}
        );
        
        // then - 역직렬화 검증
        assertNotNull(promptResponses);
        assertFalse(promptResponses.isEmpty());
        
        // when - 데이터 저장 테스트
        for (int i =0; i < companyNoList.size(); i++) {
            Long companyNo = companyNoList.get(i);
            PromptResponseDto promptResponseDto = promptResponses.get(i);

            // 각 프롬프트 응답에 대해 데이터 저장 실행
            assertDoesNotThrow(() -> {
                analysisResultService.saveAnalysisResultFromPrompt(
                    companyNo,
                    industryNo,
                    promptResponseDto
                );
            });
        }
    }
    
    /**
     * 개별 PromptResponseDto 저장 테스트
     */
    @Test
    void testSinglePromptResponseSaving() throws Exception {
        // given
        Long companyNo = 2L;
        Long industryNo = 2L;
        
        // JSON 파일에서 단일 객체 생성
        ClassPathResource resource = new ClassPathResource("prompt-responses.json");
        InputStream inputStream = resource.getInputStream();
        List<PromptResponseDto> promptResponses = objectMapper.readValue(
            inputStream, 
            new TypeReference<List<PromptResponseDto>>() {}
        );
        
        PromptResponseDto singleResponse = promptResponses.get(1); // 애플 데이터
        
        // when & then
        assertDoesNotThrow(() -> {
            analysisResultService.saveAnalysisResultFromPrompt(
                companyNo, 
                industryNo, 
                singleResponse
            );
        });
        
        // 브랜드명 검증
        assertEquals("애플", singleResponse.getBrand());
    }
    
    /**
     * JSON 역직렬화만 테스트
     */
    @Test
    void testJsonDeserialization() throws Exception {
        // given
        ClassPathResource resource = new ClassPathResource("prompt-responses.json");
        InputStream inputStream = resource.getInputStream();
        
        // when
        List<PromptResponseDto> promptResponses = objectMapper.readValue(
            inputStream, 
            new TypeReference<List<PromptResponseDto>>() {}
        );
        
        // then
        assertNotNull(promptResponses);
        assertEquals(3, promptResponses.size());
        
        // 각 브랜드 검증
        assertEquals("삼성전자", promptResponses.get(0).getBrand());
        assertEquals("애플", promptResponses.get(1).getBrand());
        assertEquals("LG전자", promptResponses.get(2).getBrand());
        
        // 모든 응답이 필수 데이터를 포함하고 있는지 검증
        for (PromptResponseDto response : promptResponses) {
            assertNotNull(response.getBrand());
            assertNotNull(response.getCategoryResults());
            assertNotNull(response.getInsightSummary());
            assertFalse(response.getCategoryResults().isEmpty());
            
            // 각 카테고리 결과 검증
            for (var categoryResult : response.getCategoryResults()) {
                assertNotNull(categoryResult.getQuestionId());
                assertNotNull(categoryResult.getCategory());
                assertNotNull(categoryResult.getAnswer());
                assertNotNull(categoryResult.getPositiveKeyword());
                assertNotNull(categoryResult.getNegativeKeyword());
                assertTrue(categoryResult.getScore() >= 0);
                assertTrue(categoryResult.getConfidence() >= 0);
                assertNotNull(categoryResult.getSupportingEvidence());
            }
        }
    }
}
