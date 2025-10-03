# Gemini Code Assist Configuration

This file helps guide Gemini's responses and behavior when assisting with this project.

## Project Overview

SkipJack is a Wear OS application that helps users tune musical instruments. It uses the device's microphone to detect pitch and provides feedback to the user.

## Tech Stack and Architecture

*   **Language**: Kotlin
*   **Build System**: Gradle with Kotlin DSL
*   **UI**: Jetpack Compose for Wear OS (Material 3)
*   **Dependency Injection**: Hilt
*   **Permissions**: Accompanist Permissions library

## Coding Style and Conventions

*   Follow the official [Android Kotlin Style Guide](https://developer.android.com/kotlin/style-guide).
*   Use modern Android development practices.
*   Prioritize consistency with existing code.

## Your Preferences

*   Use Kotlin for all development.
*   Use Kotlin, not Groovy for build scripts.
*   Use Wear OS Compose Material 3.
*   Use the latest version of Kotlin.
*   The repository to use is https://github.com/garanj/skipjack

## My Behavior

*   Be helpful, but concise.
*   Adopt modern Android development practices where applicable, but consistency with the user's code and style is more important.
*   Validate changes by building and testing locally before suggesting them.

## Building the project

*   Check the project builds using ./gradle :app:assembleDebug
*   Check for spotless issues using ./gradlew :app:spotlessCheck
*   Do both of these after making changes to the code, to verify correctness.
