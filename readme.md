## ORDER-MVP2
> 멀티 모듈 단일 프로젝트로 도메인 주도 설계(DDD)를 통해 SpringBoot 기반 이커머스 주문 서비스 개발
<br> 
사용기술: java11, SpringBoot 2.7.2, SpringBatch, JPA, myBatis, H2, mysql, swagger2

## 모듈

### common
> 특정 서비스에 의존하는 것이 아닌, 공통적인 비즈니스 및 에러처리 등 서비스 전반적으로 사용되는 모듈

### order-core
> 도메인 별 바운더리 컨텍스트로 구분 ~ 코어 모듈

### order-api-server
> 주문 서비스의 웹 어플리케이션

### order-batch-server
> 주문 서비스 배치 어플리케이션


## 브랜치 전략
> git-flow 브랜치 전략으로 브랜치 이름을 통해 기능/의도를 명확히하여 모듈 별 체계적인 개발 프로세스 구현

- /feature/모듈/기능


## docker(mysql) 
```
FSDF
```




## 요구사항


### 상품
 1. 상품은 상품명, 가격으로 구성된다.
 2. 상품은 판매 준비중, 판매중, 판매종료의 상태를 가진다.
 3. 상품은 회원의 주문을 받아 판매될 수 있다.


### 주문 
 1. 판매중인 상태의 상품만 주문할 수 있다.
 2. 최소 한 종류 이상의 상품을 주문해야 한다.
 3. 한 상품을 한 개 이상 주문할 수 있다.
 4. 총 주문 금액은 각 상품의 구매 가격 합을 모두 더한 금액이다.
 5. 각 상품의 구매 가격 합은 상품 가격에 구매 개수를 곱한 값이다.
 6. 주문할 때 배송지 정보를 반드시 지정해야 한다.
 7. 배송지 정보는 받는 사람 이름, 전화번호, 주소로 구성된다.
 8. 출고를 하면 배송지를 변경할 수 없다.
 9. 출고 전에 주문을 취소할 수 있다.
 10. 고객이 결제를 완료하기 전에는 상품을 준비하지 않는다.

### 회원

 1. 회원만이 상품을 주문할 수 있다.
 2. 회원가입 시 회원명과 email 은 필수 값이다.
 3. 회원가입이 완료되면 등록된 이메일로 가입 완료 안내 메일을 발송한다.
 4. 


