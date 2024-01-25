import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//import org.slf4j.Logger

val allureVersion = "2.24.0"
val aspectJVersion = "1.9.20.1"
val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

plugins {
    kotlin("jvm") version "1.7.20"
    id("io.qameta.allure") version "2.11.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
    implementation("org.junit.jupiter:junit-jupiter-params:5.8.0")
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-junit5")
    agent("org.aspectj:aspectjweaver:${aspectJVersion}")
   // testImplementation("io.qameta.allure:allure-junit5:3.0.0")
}


tasks.test {
    useJUnitPlatform()
}

tasks.test {
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

allure {
    version.set("2.11.0")
}