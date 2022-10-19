## ORDER-MVP2
> 주문 서비스.v2 는 회원, 상품, 주문, 쿠폰의 도메인으로 전반적인 E-cormmerce 서비스를 구현한 프로젝트입니다.
> 도메인 주도 개발(DDD)을 통해 각 객체 간 협력으로 비즈니스 로직을 작성하였으며 패키지 별 의존성을 고려하면서 개발하였습니다. <br>
>
> portfolio: https://bit.ly/3UnNLgW <br>
>
> 사용기술: java11, SpringBoot 2.7.2, SpringBatch, JPA, myBatis, H2, mysql, swagger2


<br> 

## 프로젝트 구성

### order-core
> 도메인 별 바운더리 컨텍스트로 구분, 비즈니스 로직 및 코어 모듈

### order-api-server
> 주문 서비스 웹 어플리케이션

### order-batch-server
> 상품등록 및 결제내역 일괄처리를 위한 스케줄링 + 배치 어플리케이션


## 도메인

![image](https://user-images.githubusercontent.com/59961350/196823873-a8a0f0d8-b38a-44b2-99ad-9d1db4ce248d.png)


## ERD

![image](https://user-images.githubusercontent.com/59961350/196823940-8c4dded3-eeef-417c-8671-a85ce4e5c2c8.png)


## 개발 프로세스

### 이슈등록
`fature`, `bug` label 과 같이 이슈를 생성한다. <br>
등록된 이슈는 git-flow 전략에 맞게 브랜치를 생성하여 구현한다.

### pull-request

#### [develop]
feature 기능이 완료 된 후, develop 브랜치에 pull-requests 올린다.
- 이슈내용
- 구현
- 테스트
- 기타사항


## Info

[프로젝트 문서](https://github.com/t1dmlgus/order-mvp2/wiki)

