# 필수 LV2
### 필수 단계 에서는 예외처리 X -> 일부 예외 상황은 null로 return 
___

## 📍개발 내용
### 유저 CRUD 개발
- 유저 생성 API
  - 요청 받은 데이터를 파싱한 후 해당 정보로 유저를 생성하고 spring data JPA가 제공하는 save()메서드를 제공하여 저장
  - 유저 생성 성공 시 user의 id과 username을 DTO에 담아서 ResponseEntity로 201 상태코드와 함께 반환

    
- 유저 전체 조회 API
  - spring data JPA의 findAll() 메서드를 활용하여 유저 전체를 조회
  - 전체 조회 성공 시 조회된 유저를 DTO로 반환하여 이를 List에 담아 ResponseEntity로 200 상태 코드와 함께 반환

- 유저 단건 조회 API
  - spring data JPA의 findById() 메서드를 활용하여 특정 유저를 조회
  - 유저 조회 성공시 조회된 유저를 DTO로 변환하여 ResponseEntity로 200 상태 코드와 함께 반환


- 유저 수정 API
  - JPA의 변경감지를 이용하여 findById()를 통해 조회된 유저를 요청 값을 파싱하여 entity를 수정
  - 수정 성공 시 유저의 id, username, email 정보를 DTO로 변환하여 ResponseEntity로 200 상태 코드와 함께 반환


- 유저 삭제 APi
  - spring data JPA의 delete()메서드를 활용하여 findById()로 조회된 유저를 삭제
  - 삭제 성공 시 반환값 없이 ResponseEntity로 200 상태코드만 반환