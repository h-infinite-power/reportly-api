package com.human.infinite.power.reportly.domain.industry.service;

import com.human.infinite.power.reportly.domain.category.entity.Category;
import com.human.infinite.power.reportly.domain.category.repository.CategoryRepository;
import com.human.infinite.power.reportly.domain.industry.entity.Industry;
import com.human.infinite.power.reportly.domain.industry.repository.IndustryRepository;
import com.human.infinite.power.reportly.domain.question.entity.Question;
import com.human.infinite.power.reportly.domain.question.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class IndustryServiceTest {

    @Autowired private IndustryRepository industryRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private QuestionRepository questionRepository;

    @Test
    @DisplayName("업종/카테고리/질문 초기 적재 - 금융/핀테크")
    void seedIndustryCategoryQuestion_fromFile() throws Exception {
        // 1) 업종 저장
        Long industryNo = 1483912168854887831L;

        // 2) 파일 읽기: src/test/resources/CategoryAndQuestion.txt (클래스패스)
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("CategoryAndQuestion.txt")),
                StandardCharsets.UTF_8))) {
            List<String> lines = reader.lines().toList();

            int order = 1;
            for (String raw : lines) {
                String line = raw.trim();
                if (line.isEmpty()) continue;

                // 형태: (카테고리, 질문), 혹은 (카테고리, 질문)
                if (line.startsWith("(")) {
                    line = line.substring(1);
                }
                if (line.endsWith(",")) {
                    line = line.substring(0, line.length() - 1);
                }
                if (line.endsWith(")")) {
                    line = line.substring(0, line.length() - 1);
                }

                String[] parts = line.split(",", 2);
                if (parts.length < 2) continue;
                String categoryName = parts[0].trim();
                String questionText = parts[1].trim();

                // 3) 카테고리 저장 (생성자에서 IdGenerator로 categoryNo 생성)
                Category category = categoryRepository.save(new Category(categoryName));

                // 4) 질문 저장 (생성자에서 IdGenerator로 questionNo 생성)
                Question question = new Question(
                        industryNo,
                        category.getCategoryNo(),
                        questionText,
                        order++
                );
                questionRepository.save(question);
            }
        }

        // 5) 검증
        assertThat(categoryRepository.findAll().size()).isGreaterThan(0);
        assertThat(questionRepository.findAll().size()).isGreaterThan(0);
    }
}
