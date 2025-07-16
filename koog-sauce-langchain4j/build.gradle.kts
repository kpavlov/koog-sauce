plugins {
    kotlin("plugin.serialization")
    `kotlin-convention`
    `dokka-convention`
    `publish-convention`
    alias(libs.plugins.kover)
}

description = "Koog Sauce Langchain4j Integration - A secret ingredient from The Chef."

kotlin {

    sourceSets {
        commonMain {
            dependencies {
                api(project(":koog-sauce"))
                implementation(libs.kotlinx.serialization.json)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotest.assertions.core)
                implementation(libs.mockk)
            }
        }

        jvmMain {
            dependencies {
                // JVM-specific dependencies
                api(project.dependencies.platform(libs.langchain4j.bom))
                api(libs.langchain4j.kotlin)
            }
        }

        jvmTest {
            dependencies {
                implementation(libs.kotest.runner.junit5)
                implementation(libs.mokksy.openai)
                implementation(kotlin("test"))
                implementation(libs.assertj.core)
                implementation(libs.finchly)
                implementation(libs.junit.jupiter.params)
                runtimeOnly(libs.slf4j.simple)
                implementation(libs.langchain4j.openai)
            }
        }
    }
}
