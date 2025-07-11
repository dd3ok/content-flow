plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.dd3ok"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring WebFlux for Reactive Web and Coroutines
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Reactive MongoDB Driver
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    // Jackson Module for Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Google Genai
    implementation("com.google.genai:google-genai:1.8.0")

    // cloudinary - Image, Media, Asset
    implementation("com.cloudinary:cloudinary-http5:2.0.0")
    implementation("com.cloudinary:cloudinary-taglib:2.0.0")
    implementation("io.github.cdimascio:dotenv-java:2.2.4")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
