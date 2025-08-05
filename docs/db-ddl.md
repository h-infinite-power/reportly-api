CREATE TABLE `AnalysisResult` (
  -- 분석 결과 번호 (Primary Key)
  `analysisResultNo` BIGINT NOT NULL,
  -- 회사 번호 (FK → Company.companyNo)
  `companyNo` BIGINT NOT NULL,
  -- 업종 번호 (FK → Industry.industryNo)
  `industryNo` BIGINT NOT NULL,
  -- 분석 일자
  `date` DATE NOT NULL,
  -- 요약
  `summary` VARCHAR(255) NOT NULL,
  -- 상세 내용
  `content` TEXT NOT NULL,
  -- 회사·업종 종합 점수 (반정규화 허용)
  `companyIndustryTotalScore` DOUBLE NOT NULL COMMENT '반정규화 허용',
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
  `categoryNo` BIGINT NOT NULL Primary Key,
  -- 카테고리 이름
  `categoryName` VARCHAR(255) NOT NULL,
  -- 생성 일시
  `createdAt` DATETIME NOT NULL,
  -- 수정 일시
  `modifiedAt` DATETIME NOT NULL,
  -- 비고
  `note` VARCHAR(255) NULL
);

CREATE TABLE `Industry` (
  -- 업종 번호 (Primary Key)
  `industryNo` BIGINT NOT NULL Primary Key,
  -- 업종 이름
  `industryName` VARCHAR(255) NOT NULL,
  -- 생성 일시
  `createdAt` DATETIME NOT NULL,
  -- 수정 일시
  `modifiedAt` DATETIME NOT NULL,
  -- 비고
  `note` VARCHAR(255) NULL
);

CREATE TABLE `Question` (
  -- 질문 번호 (Primary Key)
  `questionNo` BIGINT NOT NULL Primary Key,
  -- 업종 번호 (FK → Industry.industryNo)
  `industryNo` BIGINT NOT NULL,
  -- 카테고리 번호 (FK → Category.categoryNo)
  `categoryNo` BIGINT NOT NULL,
  -- 질문 내용
  `question` VARCHAR(255) NOT NULL,
  -- 정렬 순서
  `orderNo` INT NOT NULL,
  -- 생성 일시
  `createdAt` DATETIME NOT NULL,
  -- 수정 일시
  `modifiedAt` DATETIME NOT NULL,
  -- 비고
  `note` VARCHAR(255) NULL
);

CREATE TABLE `competitor` (
  -- 경쟁사 번호 (Primary Key)
  `competitorNo` BIGINT NOT NULL Primary Key,
  -- 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
  `analysisResultNo` BIGINT NOT NULL,
  -- 회사 번호 (FK → Company.companyNo)
  `companyNo` BIGINT NOT NULL,
  -- 생성 일시
  `createdAt` DATETIME NOT NULL,
  -- 수정 일시
  `modifiedAt` DATETIME NOT NULL,
  -- 비고
  `note` VARCHAR(255) NULL
);

CREATE TABLE `Keyword` (
  -- 키워드 번호 (Primary Key)
  `keywordNo` BIGINT NOT NULL Primary Key,
  -- 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
  `analysisResultNo` BIGINT NOT NULL,
  -- 키워드 유형
  `type` VARCHAR(255) NULL,
  -- 키워드 내용
  `content` VARCHAR(255) NOT NULL,
  -- 생성 일시
  `createdAt` DATETIME NOT NULL,
  -- 수정 일시
  `modifiedAt` DATETIME NOT NULL,
  -- 비고
  `note` VARCHAR(255) NULL
);

CREATE TABLE `Company` (
  -- 회사 번호 (Primary Key)
  `companyNo` BIGINT NOT NULL Primary Key,
  -- 회사 이름
  `companyName` VARCHAR(255) NOT NULL,
  -- 생성 일시
  `createdAt` DATETIME NOT NULL,
  -- 수정 일시
  `modifiedAt` DATETIME NOT NULL,
  -- 비고
  `note` VARCHAR(255) NULL
);

CREATE TABLE `AnalysisResultScore` (
  -- 분석 결과 점수 번호 (Primary Key)
  `analysisResultScoreNo` BIGINT NOT NULL Primary Key,
  -- 분석 결과 번호 (FK → AnalysisResult.analysisResultNo)
  `analysisResultNo` BIGINT NOT NULL,
  -- 카테고리 번호 (FK → Category.categoryNo)
  `categoryNo` BIGINT NOT NULL,
  -- 카테고리별 점수
  `categoryScore` FLOAT NOT NULL,
  -- 생성 일시
  `createdAt` DATETIME NOT NULL,
  -- 수정 일시
  `modifiedAt` DATETIME NOT NULL,
  -- 비고
  `note` VARCHAR(255) NULL
);
