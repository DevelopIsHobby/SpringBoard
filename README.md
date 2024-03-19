# 게시판 클론코딩(코드로 배우는 스프링 웹 프로젝트)
CRUD 게시판에 파일첨부 + 스프링시큐리티(로그인, 관리자)를 추가한 프로젝트를 

공부할 수 있는 책이어서 클론코딩을 진행해 보았습니다.

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
+ 파일첨부

### 이미지
---
+ 게시판 메인 화면
  <img src="https://github.com/DevelopIsHobby/SpringBoard/assets/107912101/1ba50979-7435-48e7-875d-2d78d0882925">
1. 페이징
2. 게시글 덧글 수 확인
3. 키워드 검색

+ 게시글 화면
<img src="https://github.com/DevelopIsHobby/SpringBoard/assets/107912101/b7feeef7-af33-43a5-b159-14d95ed8b461">

1. 자신이 작성한 게시글의 경우만 수정, 삭제 가능
2. 로그인을 해야만 댓글 작성 가능
3. 댓글 CRUD

+ 로그인 화면
<img src="https://github.com/DevelopIsHobby/SpringBoard/assets/107912101/2ed0f240-74bc-42fb-92ff-49713fadc4e4">

1. 자동로그인 기능


### 소감
---
+ 지난번 게시판과 다르게 dao를 사용하지 않고 mapper만 사용하는 것 + lombok를 사용함으로써 코드를 훨씬 간결하게 만들었다.
+ 오라클을 이용해 테이블 및 인덱스를 만들어 볼 수 있었다.
+ 파일첨부 & 스프링 시큐리티 부분은 거의 책보고 따라만 쳤다... 좀 더 공부해야겠다 ㅠㅠ
   
