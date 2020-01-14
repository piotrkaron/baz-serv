import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
    kotlin("plugin.jpa") version "1.3.61"
}

group = "pl.pwr"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    //implementation ("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("com.fasterxml.jackson.datatype:jackson-datatype-hppc")
    implementation ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation ("com.fasterxml.jackson.core:jackson-annotations")
    implementation ("com.fasterxml.jackson.core:jackson-databind")
    implementation ("com.fasterxml.jackson.module:jackson-module-afterburner")
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.+")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation ("org.mapstruct:mapstruct:1.3.1.Final")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.3.1.Final")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    runtimeOnly ("mysql:mysql-connector-java")

    testImplementation("io.mockk:mockk:1.9")
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation ("com.google.truth:truth:1.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
