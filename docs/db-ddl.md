CREATE TABLE `AnalysisResult` (
-- 분석 결과 번호 (Primary Key)
`analysisResultNo` BIGINT NOT NULL,
-- 대상기업번호 (FK → Company.companyNo)
`companyNo` BIGINT NOT NULL,
-- 업종번호 (FK → Industry.industryNo)
`industryNo` BIGINT NOT NULL,
-- 분석 일자
`date` DATE NOT NULL,
-- 요약
`summary` VARCHAR(255) NOT NULL,
-- 상세 내용
`content` TEXT NOT NULL,
-- 회사·업종 종합 점수 (반정규화 허용)
`companyIndustryTotalScore` DOUBLE NOT NULL COMMENT '원본데이터가 변경될 일이 없으므로 반정규화 허용',
-- 근거 자료
`supporting_evidence` VARCHAR(255) NOT NULL,
-- 강점
`strongPoint` TEXT NOT NULL,
-- 약점
`weakPoint` TEXT NOT NULL,
-- 개선 사항
`improvements` TEXT NOT NULL,
-- 생성 일시
`createdAt` DATETIME NOT NULL,
-- 수정 일시
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL,
CONSTRAINT `PK_ANALYSISRESULT` PRIMARY KEY (`analysisResultNo`),
UNIQUE KEY `UQ_ANALYSISRESULT_COMP_IND_DATE` (`companyNo`, `industryNo`, `date`)
);


CREATE TABLE `Category` (
-- 카테고리 번호 (Primary Key)
`categoryNo` BIGINT NOT NULL,
-- 카테고리 이름
`categoryName` VARCHAR(255) NOT NULL,
-- 생성 일시
`createdAt` DATETIME NOT NULL,
-- 수정 일시
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL,
CONSTRAINT `PK_CATEGORY` PRIMARY KEY (`categoryNo`)
);

CREATE TABLE `Job` (
-- 업무 번호 (Primary Key)
`jobNo` BIGINT NOT NULL,
-- 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
`analysisResultNo` BIGINT NOT NULL,
-- 생성 일시
`createdAt` DATETIME NOT NULL,
-- 수정 일시
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL,
CONSTRAINT `PK_JOB` PRIMARY KEY (`jobNo`)
);

CREATE TABLE `Industry` (
-- 업종 번호 (Primary Key)
`industryNo` BIGINT NOT NULL,
-- 업종 이름
`industryName` VARCHAR(255) NOT NULL,
-- 생성 일시
`createdAt` DATETIME NOT NULL,
-- 수정 일시
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL,
CONSTRAINT `PK_INDUSTRY` PRIMARY KEY (`industryNo`)
);

CREATE TABLE `Question` (
-- 질문 번호 (Primary Key)
`questionNo` BIGINT NOT NULL,
-- 업종 번호 (FK → Industry.industryNo)
`industryNo` BIGINT NOT NULL,
-- 카테고리 번호 (FK → Category.categoryNo)
`categoryNo` BIGINT NOT NULL,
-- 질문 내용
`question` VARCHAR(255) NOT NULL,
-- 순서 번호
`orderNo` INT NOT NULL,
-- 생성 일시
`createdAt` DATETIME NOT NULL,
-- 수정 일시
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL,
CONSTRAINT `PK_QUESTION` PRIMARY KEY (`questionNo`)
);

CREATE TABLE `AnalysisResultJob` (
-- 분석 결과-업무 매핑 번호 (Primary Key)
`analysisResultJobNo` BIGINT NOT NULL,
-- 업무 번호 (FK → Job.jobNo)
`jobNo` BIGINT NOT NULL,
-- 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
`analysisResultNo` BIGINT NOT NULL,
-- 생성 일시
`createdAt` DATETIME NOT NULL,
-- 수정 일시
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL,
CONSTRAINT `PK_ANALYSISRESULTJOB` PRIMARY KEY (`analysisResultJobNo`)
);

CREATE TABLE `Keyword` (
-- 키워드 번호 (Primary Key)
`keywordNo` BIGINT NOT NULL,
-- 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
`analysisResultNo` BIGINT NOT NULL,
-- 키워드 타입
`type` VARCHAR(255) NULL,
-- 키워드 내용
`content` VARCHAR(255) NOT NULL,
-- 생성 일시
`createdAt` DATETIME NOT NULL,
-- 수정 일시
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL,
CONSTRAINT `PK_KEYWORD` PRIMARY KEY (`keywordNo`)
);

CREATE TABLE `Company` (
-- 회사 번호 (Primary Key)
`companyNo` BIGINT NOT NULL,
-- 회사 이름
`companyName` VARCHAR(255) NOT NULL,
-- 생성 일시
`createdAt` DATETIME NOT NULL,
-- 수정 일시
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL,
CONSTRAINT `PK_COMPANY` PRIMARY KEY (`companyNo`)
);

CREATE TABLE `AnalysisResultScore` (
-- 분석 결과 점수 번호 (Primary Key)
`analysisResultScoreNo` BIGINT NOT NULL,
-- 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
`analysisResultNo` BIGINT NOT NULL,
-- 카테고리 번호 (FK → Category.categoryNo)
`categoryNo` BIGINT NOT NULL,
-- 카테고리 점수
`categoryScore` FLOAT NOT NULL,
-- 생성 일시
`createdAt` DATETIME NOT NULL,
-- 수정 일시
`modifiedAt` DATETIME NOT NULL,
-- 비고
`note` VARCHAR(255) NULL,
CONSTRAINT `PK_ANALYSISRESULTSCORE` PRIMARY KEY (`analysisResultScoreNo`)
);
