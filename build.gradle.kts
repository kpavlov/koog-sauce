plugins {
    base
    alias(libs.plugins.detekt)
    alias(libs.plugins.kover)
    `dokka-convention`
    alias(libs.plugins.nexusPublish) // https://github.com/gradle-nexus/publish-plugin
    kotlin("plugin.serialization") version libs.versions.kotlin apply false
    signing
}

allprojects {
    repositories {
        mavenCentral()
    }
}

// Common configuration for subprojects
subprojects {
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "org.jetbrains.dokka-javadoc")
    apply(plugin = "com.diffplug.spotless")
}

dependencies {
    kover(project(":koog-sauce"))
    kover(project(":koog-sauce-langchain4j"))
    kover(project(":koog-sauce-spring-ai"))
    kover(project(":examples"))
}

kover {
    reports {

        total {
            xml
            html
        }

        verify {
            rule {
                bound {
                    minValue = 85
                }
            }
        }
    }
}
