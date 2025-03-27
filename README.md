# 필수 LV1
### 필수 단계 에서는 예외처리 X -> 일부 예외 상황은 null로 return 
___

## 📍개발 내용
### 일정 CRUD 개발
- 일정 생성 API
  - 요청받은 데이터를 파싱한 후 일정을 생성한 뒤 spring data JPA가 제공하는 save() 메서드를 활용하여 저장
  - 일정 생성 성공 시 반환 값으로 생성한 일정의 id값을 생성과 수정 공통으로 사용하는 DTO에 담아 ResponseEntity로 201 상태코드와 함께 반환


- 전체 조회 API
  - spring data JPA에서 제공하는 findAll() 메서드를 활용하여 schedule 테이블 전체를 조회
  - 조회 전용 DTO 객체를 생성하여 DTO에서 static 메서드를 통해 entity를 DTO로 변환하는 메서드를 작성
  - 전체 조회 성공 시 해당 메서드를 통해 조회된 일정들을 DTO로 변환하여 List 자료구조에 담아 ResponseEntity로 200 상태코드와 함께 반환


- 일정 단건 조회
  - spring data JPA에서 제공하는 findById() 메서드를 활용하여 단건을 조회
  - 일정 단건 조회 성공 시 전체 조회 API에서 활용한 DTO 변환 메서드를 통해 DTO로 변환한 후 ResponseEntity로 200 상태코드와 함께 반환


- 일정 수정 API
  - JPA의 변경감지를 활용하여 findById() 메서드로 조회한 일정 entity를 변경 적용
  - schedule entity 에서 업데이트를 위한 메서드를 생성(null 체크 포함)
  - 수정 성공 시 일정의 id값을 반환하는 공통 DTO를 ResponseEntity로 200 상태코드와 함께 반환


- 일정 삭제 API
  - spring data JPA에서 제공하는 delete() 메서드를 활용
  - 먼저 findById()로 조회한 후 null 체크를 하고 그 뒤에 delete()에 조회한 메서드를 인자로 넘겨서 삭제를 진행
  - 삭제 성공시 반환값 없이 ResponseEntity에 200 상태 코드만 반환 