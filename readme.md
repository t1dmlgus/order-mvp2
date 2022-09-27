## ORDER-MVP2
> 주문 서비스.v2 는 회원, 상품, 주문, 쿠폰 등 전반적인 이커머스 서비스를 구현한 프로젝트입니다.
> - 도메인 주도 개발(DDD)을 통해 각 객체 간 협력으로 비즈니스 로직을 작성하며, 의존성을 고려하며 개발
> - 핵심모듈(도메인)을 의존하는 단일프로젝트 멀티모듈(웹 어플리케이션 + 배치 어플리케이션) 구성
> <br> 
> 사용기술: java11, SpringBoot 2.7.2, SpringBatch, JPA, myBatis, H2, mysql, swagger2


<br> 

## 프로젝트 구성

### order-core
> 도메인 별 바운더리 컨텍스트로 구분, 비즈니스 로직 및 코어 모듈

### order-api-server
> 주문 서비스 웹 어플리케이션

### order-batch-server
> 상품등록 및 결제내역 일괄처리를 위한 스케줄링 + 배치 어플리케이션


## 실행

docker(mysql) 
```

```

## 업데이트 내역
> v1.0


## Info

[프로젝트 문서](https://github.com/t1dmlgus/order-mvp2/wiki)

