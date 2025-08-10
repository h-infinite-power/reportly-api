package com.human.infinite.power.reportly.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 데이터베이스 연결 설정을 관리하는 설정 클래스
 * 운영 환경에서는 환경변수로 전달된 JSON 형태의 DB 설정을 사용합니다.
 */
@Configuration
@Profile("!local")
@RequiredArgsConstructor
public class DataSourceConfig {

    @Value("${DB_CONFIG_JSON:}")
    private String dbConfigJson;

    /**
     * 운영 환경용 DataSource 설정
     * 환경변수 DB_CONFIG_JSON에서 데이터베이스 설정을 읽어와 DataSource를 생성합니다.
     * 
     * @return DataSource 객체
     * @throws Exception JSON 파싱 오류 또는 설정 오류 시 예외 발생
     */
    @Bean
    public DataSource dataSource() throws Exception {
        if (dbConfigJson == null || dbConfigJson.trim().isEmpty()) {
            throw new IllegalArgumentException("DB_CONFIG_JSON 환경변수가 설정되지 않았습니다.");
        }

        // JSON 형태의 DB 설정을 파싱
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> dbConfig = mapper.readValue(dbConfigJson, Map.class);

        // DB 설정 값 추출
        String url = dbConfig.get("url");
        String username = dbConfig.get("username");
        String password = dbConfig.get("password");

        // 필수 설정값 검증
        if (url == null || username == null || password == null) {
            throw new IllegalArgumentException("DB 설정 JSON에 필수 값(url, username, password)이 누락되었습니다.");
        }

        // DataSource 생성 및 설정
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        
        return dataSource;
    }
}
