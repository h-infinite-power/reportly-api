CREATE TABLE `AnalysisResult` (
-- 분석 결과 번호 (Primary Key)
`analysisResultNo` BIGINT NOT NULL PRIMARY KEY,
-- 대상 기업 번호 (FK → Company.companyNo)
`companyNo` BIGINT NOT NULL,
-- 업종 번호 (FK → Industry.industryNo)
`industryNo` BIGINT NOT NULL,
-- 분석 일자
`date` DATE NOT NULL,
-- 요약
`summary` VARCHAR(255) NOT NULL,
-- 내용
`content` TEXT NOT NULL,
-- 회사업종전체점수
`companyIndustryTotalScore` DOUBLE NOT NULL,
-- 이유
`supporting_evidence` VARCHAR(255) NOT NULL,
-- 강점
`strongPoint` TEXT NOT NULL,
-- 약점
`weakPoint` TEXT NOT NULL,
-- 개선제안
`improvements` TEXT NOT NULL,
-- 생성일자
`createdAt` DATETIME NOT NULL,
-- 수정일자
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL,
UNIQUE KEY `UQ_ANALYSISRESULT_COMP_IND_DATE` (`companyNo`, `industryNo`, `date`)
);

CREATE TABLE `Category` (
-- 카테고리 번호 (Primary Key)
`categoryNo` BIGINT NOT NULL PRIMARY KEY,
-- 카테고리 이름
`categoryName` VARCHAR(255) NOT NULL,
-- 생성일자
`createdAt` DATETIME NOT NULL,
-- 수정일자
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL
);

CREATE TABLE `Industry` (
-- 업종 번호 (Primary Key)
`industryNo` BIGINT NOT NULL PRIMARY KEY,
-- 업종 이름
`industryName` VARCHAR(255) NOT NULL,
-- 생성일자
`createdAt` DATETIME NOT NULL,
-- 수정일자
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL
);

CREATE TABLE `Question` (
-- 질문 번호 (Primary Key)
`questionNo` BIGINT NOT NULL PRIMARY KEY,
-- 업종 번호 (FK → Industry.industryNo)
`industryNo` BIGINT NOT NULL,
-- 카테고리 번호 (FK → Category.categoryNo)
`categoryNo` BIGINT NOT NULL,
-- 질문
`question` VARCHAR(255) NOT NULL,
-- 정렬순서
`orderNo` INT NOT NULL,
-- 생성일자
`createdAt` DATETIME NOT NULL,
-- 수정일자
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL
);

CREATE TABLE `AnalysisResultJob` (
-- 분석 결과 작업 번호 (Primary Key)
`analysisResultJobNo` BIGINT NOT NULL PRIMARY KEY,
-- 작업 번호 (FK → Job.jobNo)
`jobNo` BIGINT NOT NULL,
-- 경쟁사 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
`analysisResultNo` BIGINT NOT NULL,
-- 생성일자
`createdAt` DATETIME NOT NULL,
-- 수정일자
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL
);

CREATE TABLE `Keyword` (
-- 키워드 번호 (Primary Key)
`keywordNo` BIGINT NOT NULL PRIMARY KEY,
-- 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
`analysisResultNo` BIGINT NOT NULL,
-- 타입
`type` VARCHAR(255) NULL,
-- 내용
`content` VARCHAR(255) NOT NULL,
-- 생성일자
`createdAt` DATETIME NOT NULL,
-- 수정일자
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL
);

CREATE TABLE `Company` (
-- 기업 번호 (Primary Key)
`companyNo` BIGINT NOT NULL PRIMARY KEY,
-- 기업 이름
`companyName` VARCHAR(255) NOT NULL,
-- 생성일자
`createdAt` DATETIME NOT NULL,
-- 수정일자
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL
);

CREATE TABLE `AnalysisResultScore` (
-- 분석 결과 점수 번호 (Primary Key)
`analysisResultScoreNo` BIGINT NOT NULL PRIMARY KEY,
-- 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
`analysisResultNo` BIGINT NOT NULL,
-- 카테고리 번호 (FK → Category.categoryNo)
`categoryNo` BIGINT NOT NULL,
-- 카테고리별 점수
`categoryScore` FLOAT NOT NULL,
-- 생성일자
`createdAt` DATETIME NOT NULL,
-- 수정일자
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL
);

CREATE TABLE `Job` (
-- 작업 번호 (Primary Key)
`jobNo` BIGINT NOT NULL PRIMARY KEY,
-- 타겟 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
`analysisResultNo` BIGINT NOT NULL,
-- 생성일자
`createdAt` DATETIME NOT NULL,
-- 수정일자
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL
);

CREATE TABLE `Competitor` (
-- 경쟁사 번호 (Primary Key)
`competitorNo` BIGINT NOT NULL PRIMARY KEY,
-- 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
`analysisResultNo` BIGINT NOT NULL,
-- 회사 번호 (FK → Company.companyNo)
`companyNo` BIGINT NOT NULL,
-- 생성일자
`createdAt` DATETIME NOT NULL,
-- 수정일자
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL
);
