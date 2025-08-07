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
        Long companyNo = 1L;
        Long industryNo = 1L;
        
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
        assertEquals(3, promptResponses.size());
        
        // 첫 번째 프롬프트 응답 검증
        PromptResponseDto firstResponse = promptResponses.get(0);
        assertEquals("삼성전자", firstResponse.getBrand());
        assertNotNull(firstResponse.getCategoryResults());
        assertFalse(firstResponse.getCategoryResults().isEmpty());
        assertEquals(7, firstResponse.getCategoryResults().size());
        assertNotNull(firstResponse.getInsightSummary());
        
        // 카테고리 결과 검증
        var firstCategory = firstResponse.getCategoryResults().get(0);
        assertEquals(10L, firstCategory.getQuestionId());
        assertEquals("인지도", firstCategory.getCategory());
        assertNotNull(firstCategory.getAnswer());
        assertNotNull(firstCategory.getPositiveKeyword());
        assertNotNull(firstCategory.getNegativeKeyword());
        assertTrue(firstCategory.getScore() > 0);
        assertTrue(firstCategory.getConfidence() > 0);
        assertNotNull(firstCategory.getSupportingEvidence());
        
        // 인사이트 요약 검증
        var insightSummary = firstResponse.getInsightSummary();
        assertNotNull(insightSummary.getStrengths());
        assertNotNull(insightSummary.getWeaknesses());
        assertNotNull(insightSummary.getRecommendations());
        
        // when - 데이터 저장 테스트
        for (PromptResponseDto promptResponse : promptResponses) {
            // 각 프롬프트 응답에 대해 데이터 저장 실행
            assertDoesNotThrow(() -> {
                analysisResultService.saveAnalysisResultFromPrompt(
                    companyNo, 
                    industryNo, 
                    promptResponse
                );
            });
        }
        
        // 저장 성공 검증 (예외가 발생하지 않으면 성공)
        // 실제 프로젝트에서는 저장된 데이터를 조회하여 검증할 수 있습니다.
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
