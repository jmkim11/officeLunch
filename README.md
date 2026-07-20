# Office Lunch

Office Lunch는 직장인이 제한된 점심시간 안에 회사 주변 식당을 빠르게 결정하도록 돕는 서비스입니다.

## Current status

사전 등록된 식당 후보를 사용하는 추천 API 백엔드 1차 MVP를 구현했습니다.

- 음식 카테고리별 추천 세션 생성과 첫 추천
- 같은 세션에서 중복 없는 다음 추천
- 실제 추천받은 식당만 최종 선택
- Controller-Service-Repository 구조
- class 기반 DTO와 Bean Validation
- 공통 커스텀 오류 응답
- 도메인, Repository, Service, Controller 테스트

현재 Repository는 외부 인프라 없이 실행 가능한 인메모리 구현입니다. 회사 검색, 위치 기반 주변 식당 조회, DB 영속화, 외부 지도, 인프라와 배포는 이번 범위에 포함하지 않습니다.

## API

| 기능 | Method | Endpoint |
| --- | --- | --- |
| 세션 생성과 첫 추천 | `POST` | `/api/recommendations/sessions` |
| 다음 추천 | `POST` | `/api/recommendations/sessions/{sessionId}/next` |
| 식당 선택 | `POST` | `/api/recommendations/sessions/{sessionId}/selection` |

## Local development

요구 환경은 Java 21입니다.

```bash
./gradlew test
./gradlew bootRun
```

Windows PowerShell에서는 `.\gradlew.bat test`, `.\gradlew.bat bootRun`을 사용합니다.

기능별 설계와 완료 체크리스트는 `docs/design`에 있습니다.

## Rebuild

초기 구현에서는 회사 검색 기능부터 만들었지만, 핵심 사용자 흐름과 완료 기준을 먼저 검증하지 못했습니다. 이 저장소는 다음 순서로 제품과 백엔드를 다시 구성합니다.

1. 사용자 문제와 성공 기준 정의
2. 핵심 사용자 흐름과 비즈니스 규칙 명세
3. API와 데이터 모델 설계
4. 작은 단위의 구현과 자동화 테스트
5. 실제 실행 환경 배포와 관측
6. 측정 결과를 바탕으로 개선

## Branch workflow

- `main`: 항상 실행 및 배포 가능한 상태
- `feat/*`: 사용자 가치가 추가되는 기능
- `fix/*`: 결함 수정
- `docs/*`: 요구사항과 설계 기록
- `chore/*`: 빌드, CI, 저장소 관리

모든 변경은 작업 브랜치에서 시작하며 Pull Request의 테스트와 검토를 거쳐 `main`에 반영합니다.

## AI usage

AI는 요구사항 초안, 대안 탐색, 코드 리뷰, 테스트 누락 점검에 활용합니다. 생성 결과는 직접 설명하고 수정할 수 있는 범위에서만 반영하며, 테스트와 공식 문서를 통해 검증합니다.
