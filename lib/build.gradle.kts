plugins {
    kotlin("plugin.serialization")
    `kotlin-convention`
    `dokka-convention`
    `publish-convention`
    alias(libs.plugins.kover)
}

description = "Sample Kotlin Multiplatform Library"

kotlin {

    sourceSets {
        commonMain {
            dependencies {
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
                runtimeOnly(libs.slf4j.simple)
            }
        }

        jvmTest {
            dependencies {
                implementation(libs.kotest.runner.junit5)
            }
        }
    }
}
