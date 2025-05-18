# 목적
* Kotlin API Server 기본적인 환경 구축 프로젝트
---
# 환경
```
- SpringBoot : 3.3.10
- SpringSecurity: 6.3.8
- JDK : 17
- Mysql : 9.0.1
- Redis : 7.2.6
- Kafka : 3.9.0
```

---
# 기본 적용 라이브러리
* Auth : SpringSecurity, JWT, SMTP, Redis
* Datasource : JPA(HIKARI CP) + Mybatis
* Pub/Sub : Apache Kafka
* DotEnv

# Func
* 회원가입/로그인/로그아웃 - 완료
* 이메일 인증/로그인 이력 - 진행 중
* 관리자 - 토큰 만료 - 예정

---

## System Design

![Image](https://github.com/user-attachments/assets/577c1998-72a4-4800-be8e-895fbe630dbe)






