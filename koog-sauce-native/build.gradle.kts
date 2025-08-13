import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("plugin.serialization")
    kotlin("multiplatform")
    `dokka-convention`
    `publish-convention`
    alias(libs.plugins.kover)
    id("io.github.ttypic.swiftklib") version "0.6.4"
}

kotlin {

    val xcf = XCFramework()
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            xcf.add(this)
        }
        it.compilations {
            val main by getting {
                cinterops {
                    create("KoogSauceNative")
                }
            }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(project.dependencies.platform(libs.ktor.bom))
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.client.cio)
                implementation(libs.openai.client)
            }
        }

        iosTest {
            dependencies {
                implementation(libs.ktor.client.logging)
            }
        }
    }
}

swiftklib {
    create("KoogSauceNative") {
        path = file(path = "src/native/KoogSauceNative")
        packageName(name = "me.kpavlov.koog.sauce.native")
        minIos = 15
    }
}
