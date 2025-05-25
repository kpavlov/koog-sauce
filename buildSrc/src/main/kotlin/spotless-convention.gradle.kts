import com.diffplug.gradle.spotless.SpotlessExtension

configure<SpotlessExtension> {
    ratchetFrom("origin/main")

    kotlinGradle {
        ktlint()
    }

    kotlin {
        ktfmt().kotlinlangStyle()
        toggleOffOn()
    }

    format("misc") {
        target("*.md", ".gitignore")
        trimTrailingWhitespace()
    }
}
