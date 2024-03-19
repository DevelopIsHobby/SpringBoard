# 스프링을 활용한 복잡한 게시판 프로젝트
코드로 배우는 스프링 웹 프로젝트를 참고하여

파일첨부 + 스프링시큐리티(로그인, 관리자)를 추가한 프로젝트를 만들어보았습니다.

## 기술스택
+ Java 11
+ Spring Framework
+ Spring Security
+ MyBatis
+ Oracle

## 구현기능
+ 로그인, 로그아웃
+ 게시글 CRUD
+ 댓글 CRUD, 댓글 수 확
+ 키워드 검색 및 페이징
+ 파일첨부, 썸네일

### 이미지
---
+ 게시판 메인 화면
  <img src="https://github.com/DevelopIsHobby/SpringBoard/assets/107912101/1ba50979-7435-48e7-875d-2d78d0882925">
1. 페이징
2. 게시글 덧글 수 확인
3. 키워드 검색

---
+ 게시글 화면
<img src="https://github.com/DevelopIsHobby/SpringBoard/assets/107912101/b7feeef7-af33-43a5-b159-14d95ed8b461">

1. 자신이 작성한 게시글의 경우만 수정, 삭제 가능
2. 로그인을 해야만 댓글 작성 가능
3. 댓글 CRUD(댓글을 클릭하면 수정 및 삭제 가능)
  <img src="https://github.com/DevelopIsHobby/SpringBoard/assets/107912101/dc5382e1-670b-450c-8f72-1c7613638447">
4. 이미지 파일 클릭시 확대(다시 한번 클릭하면 사라짐)

   
   <img src="https://github.com/DevelopIsHobby/SpringBoard/assets/107912101/46da5466-fb99-4aaf-bef5-dc8ebd58aa3a">
5. 이미지 파일이 아닐경우 다른 이미지 사용(파일 이름 클릭하면 다운로드 진행)

   
<img src="https://github.com/DevelopIsHobby/SpringBoard/assets/107912101/71726ff6-208d-4ac7-9ec6-eccd67020a30">

---

+ 게시글 등록 화면
<img src="https://github.com/DevelopIsHobby/SpringBoard/assets/107912101/91230af4-de24-4106-8bc4-cce7cf07524f">

---

+ 게시글 수정 화면
  <img src="https://github.com/DevelopIsHobby/SpringBoard/assets/107912101/2d5f5de6-4174-450b-b39d-b34203640005">

---

+ 로그인 화면
<img src="https://github.com/DevelopIsHobby/SpringBoard/assets/107912101/2ed0f240-74bc-42fb-92ff-49713fadc4e4">

1. 자동로그인 기능(Remember-me 클릭시 로그인 상태 유지)

---
### 소감
---
+ 지난번 게시판과 다르게 dao를 사용하지 않고 mapper만 사용하는 것 + lombok를 사용함으로써 코드를 훨씬 간결하게 만들었다.
+ 오라클을 이용해 테이블을 만들고 인덱스르 활용해보았다.
+ 파일첨부 & 스프링 시큐리티 부분은 거의 책 보고 따라쳤다. 공부가 더 필요한듯
   
