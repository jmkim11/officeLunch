# Office Lunch

직장인의 반복적인 점심 메뉴 결정 피로를 줄이기 위한 식당 추천 MVP입니다.

## 핵심 가설

- 사용자는 많은 식당 목록보다 한 곳의 추천을 받으면 더 빠르게 결정할 수 있다.
- 다시 추천할 때 이전 결과를 제외하면 반복 추천에 대한 불만을 줄일 수 있다.

## 구현 범위

- 회사 위치 검색 API
- 추천 세션 생성
- 이전 추천을 제외한 재추천
- 추천받은 식당만 선택 가능
- 선택 완료 후 재추천 차단
- 추천 이력 저장 및 중복 방지
- 공통 예외 응답
- 핵심 비즈니스 규칙 테스트

## 기술 스택

- Java 21
- Spring Boot 4.1
- Spring MVC
- Spring Data JPA
- H2 (로컬 MVP)
- PostgreSQL/Testcontainers 의존성 준비
- Gradle

## 실행

```bash
./gradlew bootRun
```

기본 데모 데이터:

- 회사명: `포스타입`
- 카테고리: `KOREAN`, `WESTERN`

## API

### 추천 생성

```http
POST /api/recommendations
Content-Type: application/json

{
  "companyName": "포스타입",
  "category": "KOREAN"
}
```

### 다시 추천

```http
POST /api/recommendations/{sessionId}/retry
```

### 식당 선택

```http
POST /api/recommendations/{sessionId}/selection
Content-Type: application/json

{
  "restaurantId": 1
}
```

## 주요 설계 결정

### 추천 세션과 추천 이력 분리

추천 세션은 사용자 흐름의 상태를 관리하고 추천 이력은 이미 노출한 식당을 기록합니다. 이 구조로 재추천 시 이전 식당을 제외하고, 추천받지 않은 식당 선택을 검증할 수 있습니다.

### 애플리케이션과 DB 양쪽의 중복 방지

서비스는 기존 이력을 조회해 후보를 제외하고, DB에는 `(session_id, restaurant_id)` 유니크 제약조건을 둡니다.

### 과도한 인프라 제외

초기 MVP에서는 Redis, Kafka, MSA를 도입하지 않았습니다. 먼저 핵심 사용자 흐름과 데이터 정합성을 검증하고 실제 병목이 확인되면 확장합니다.

## 테스트

```bash
./gradlew test
```

검증하는 규칙:

- 재추천 시 이전 식당 제외
- 추천받지 않은 식당 선택 거부
- 선택 완료 세션 재추천 거부
- 후보 소진 시 예외 발생

## 리뷰 포인트

1. `RecommendationSession`이 상태 변경 책임을 갖는 이유
2. 추천 이력을 별도 엔티티로 분리한 이유
3. `@Transactional` 범위가 적절한지
4. 동시 재추천 요청에서 발생 가능한 경쟁 조건
5. 실제 장소 API를 연결할 때 트랜잭션 밖으로 분리해야 할 영역

## 다음 단계

- 실제 장소 검색 Adapter 연결
- PostgreSQL/Flyway 전환
- Controller 통합 테스트
- Docker 실행 환경
- 최소 사용자 테스트 및 피드백 반영
