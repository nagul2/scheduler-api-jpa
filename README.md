# 도전 LV7
## 📍개발 내용
### 일정 댓글 API 개발
- Comment SQL문 추가 및 Comment Entity 개발
  - User, Schedule 연관관계 설정


- 댓글 생성, 수정, 삭제, 전체 조회, 단건 조회 API 개발
  - spring data JPA의 메서드 이름 쿼리 활용
  - 댓글 조회 시 scheduleId에 해당되는 댓글만 조회되도록 설정
  - findCommentByIdAndScheduleIdOElseThrow(): ScheduleId와 댓글 Id로 조회된 대상의 Optional을 까서 반환하는 로직 메서드화


- Comment 관련 예외 추가 및 응답 로직 추가
  - NotFoundCommentException 추가
  - GlobalExceptionHandler의 notFoundException()에 해당 예외가 응답되도록 작성

### Refactor
- 일정 생성 Service에서 유저 조회 시 못찾을 때 예외 응답하는 로직 빠진것 추가
- 일정, 유저 전체 조회 예외처리 빠진것 추가