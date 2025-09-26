### 프로젝트1

이 프로젝트는 사용자 관리 및 게시판 기능을 포함하는 간단한 웹 애플리케이션입니다.

### 주요 기능

* **사용자 관리**: 회원가입, 로그인, 로그아웃, 회원 정보 수정 및 삭제 기능
* **게시판**: 공지사항 작성, 조회, 검색, 수정, 삭제 기능
* **보안**: SHA-512 알고리즘을 사용한 비밀번호 암호화
* **관리자 기능**: 관리자만 접근 가능한 회원 목록 조회 및 공지사항 작성 기능
* **API 엔드포인트**: 사용자 및 게시글 정보를 JSON 형식으로 제공하는 REST API

### 사용 기술

* **백엔드**:
    * Java 21
    * Spring Boot (버전 3.5.5)
    * Maven
* **프론트엔드**:
    * Thymeleaf (템플릿 엔진)
    * HTML, CSS
* **데이터베이스**:
    * SQLite
    * SQLite-JDBC (버전 3.34.0)

### 데이터베이스 스키마

`DB.java` 파일에 정의된 테이블 스키마는 다음과 같습니다:

**user 테이블:**
* `idx`: `INTEGER` (Primary Key, Auto-increment)
* `user_type`: `TEXT`
* `id`: `TEXT`
* `pwd`: `TEXT`
* `name`: `TEXT`
* `phone`: `TEXT`
* `address`: `TEXT`
* `created`: `TEXT`
* `last_updated`: `TEXT`

**post 테이블:**
* `idx`: `INTEGER` (Primary Key, Auto-increment)
* `title`: `TEXT`
* `contents`: `TEXT`
* `writer`: `TEXT`
* `created`: `TEXT`
* `last_updated`: `TEXT`
* `title_chosung`: `TEXT` (제목의 초성을 저장)

### API 엔드포인트

`ApiController.java`에 정의된 주요 REST API 엔드포인트입니다:

* `GET /create`: 데이터베이스 테이블을 생성합니다.
* `GET /selectAllUser`: 모든 사용자 목록을 반환합니다.
* `GET /selectAllPost`: 모든 게시글 목록을 반환합니다.
* `GET /selectPostRecent`: 최신 게시글 5개를 반환합니다.
* `GET /selectPostUpdateRecent`: 최근 수정된 게시글 5개를 반환합니다.
* `GET /searchPost?keyword={keyword}`: 제목, 내용, 또는 제목 초성에 {keyword}가 포함된 게시글을 검색하여 반환합니다.
