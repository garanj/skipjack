// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.diffplug.spotless) apply false
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.com.google.dagger.hilt.android) apply false
    alias(libs.plugins.com.google.devtools.ksp) apply false
}

subprojects {
    apply(plugin = "com.diffplug.spotless")

    extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")

            ktlint(libs.versions.ktlint.get()).editorConfigOverride(
                mapOf(
                    "ktlint_official" to "disabled",
                    "ktlint_standard_package-name" to "disabled",
                    "ktlint_standard_filename" to "disabled",
                    "ktlint_function_naming_ignore_when_annotated_with" to "Composable"
                )
            )
        }
    }
}
