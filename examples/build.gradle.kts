plugins {
    kotlin("jvm")
    application
    alias(libs.plugins.kover)
}

description = "Examples for Kotlin Multiplatform Library"

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    implementation(project(":koog-sauce"))
    implementation(project(":koog-sauce-langchain4j"))
    implementation(project(":koog-sauce-spring-ai"))
    implementation(libs.kotlinx.serialization.json)

    // Environment variables from .env file
    implementation(libs.finchly)

    // LangChain4j dependencies
    implementation(platform(libs.langchain4j.bom))
    implementation(libs.langchain4j.kotlin)
    implementation(libs.langchain4j.openai)

    // Spring AI dependencies
    implementation(platform(libs.spring.ai.bom))
    implementation(libs.spring.ai.openai)

    // Logging
    runtimeOnly(libs.slf4j.simple)

    // Testing
    testImplementation(kotlin("test"))
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.assertj.core)
}

application {
    // You can change this to run different examples
    // mainClass.set("me.kpavlov.koog.sauce.examples.LangChain4jAIAgentExampleKt")
    mainClass.set("me.kpavlov.koog.sauce.examples.SpringAiExampleKt")
}

tasks.withType<JavaExec>().configureEach {
    jvmArgs = listOf("-Xms512m", "-Xmx1024m")
}
