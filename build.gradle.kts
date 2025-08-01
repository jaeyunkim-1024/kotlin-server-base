plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.10-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "api.server"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

extra["snippetsDir"] = file("build/generated-snippets")


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.redisson:redisson-spring-boot-starter:3.27.1")

	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.25")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4")
	implementation("org.springframework.kafka:spring-kafka")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.4")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

//	implementation("org.springframework.boot:spring-boot-starter-batch")
//	testImplementation("org.springframework.batch:spring-batch-test")

//	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	// https://mvnrepository.com/artifact/org.springframework.security/spring-security-core
	implementation("org.springframework.security:spring-security-core:6.4.3")
	implementation("org.springframework.security:spring-security-web:6.4.3")
	implementation("org.springframework.security:spring-security-config:6.4.3")
	testImplementation("org.springframework.security:spring-security-test:6.4.3")


	implementation("io.github.cdimascio:dotenv-java:3.1.0")

	/// JWT 적용
	val JJWT_VERSION="0.12.6"
	implementation("io.jsonwebtoken:jjwt-api:${JJWT_VERSION}")
	implementation("io.jsonwebtoken:jjwt-jackson:${JJWT_VERSION}")
	implementation("io.jsonwebtoken:jjwt-impl:${JJWT_VERSION}")

	/// h2
	implementation("com.h2database:h2")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
		jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	outputs.dir(project.extra["snippetsDir"]!!)
}

tasks.asciidoctor {
	inputs.dir(project.extra["snippetsDir"]!!)
	dependsOn(tasks.test)
}
