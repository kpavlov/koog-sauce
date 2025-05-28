plugins {
    kotlin("plugin.serialization")
    `kotlin-convention`
    `dokka-convention`
    `publish-convention`
    alias(libs.plugins.kover)
}

description = "Koog Sauce - A secret ingredient from The Chef."

java {
    registerFeature("springAi") {
        usingSourceSet(sourceSets.jvmMain.get())
    }

}

kotlin {

    sourceSets {
        commonMain {
            dependencies {
                api(libs.koog)
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
                implementation(libs.spring.ai.client.chat)
                api(libs.kotlinx.coroutines.reactive)
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
                implementation(libs.spring.ai.openai)
                implementation(project.dependencies.platform(libs.spring.ai.bom))
                runtimeOnly(libs.slf4j.simple)
            }
        }
    }
}
