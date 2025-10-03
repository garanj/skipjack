plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.compose.compiler)
}

android {
    compileSdk = 36

    defaultConfig {
        applicationId = "com.garan.skipjack"
        minSdk = 30
        targetSdk = 35
        versionCode = 7
        versionName = "1.4.0"
    }

    signingConfigs {
        create("release") {
            val keystorePath = System.getenv("SIGNING_KEYSTORE_PATH")
            val keystorePassword = System.getenv("SIGNING_KEYSTORE_PASSWORD")
            val keyAliasName = System.getenv("SIGNING_KEY_ALIAS")
            val keyPasswordValue = System.getenv("SIGNING_KEY_PASSWORD")

            if (keystorePath != null && keystorePassword != null && keyAliasName != null && keyPasswordValue != null) {
                storeFile = file(keystorePath)
                storePassword = keystorePassword
                keyAlias = keyAliasName
                keyPassword = keyPasswordValue
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.garan.skipjack"
}

dependencies {
    implementation(libs.androidx.wear)

    // General compose dependencies
    implementation(libs.androidx.activity.compose)
    implementation(libs.wear.compose.material3)
    implementation(libs.wear.compose.foundation)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.compose.material.icons.extended)

    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.wear.compose.ui.tooling)

    // Hilt
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Permissions
    implementation(libs.accompanist.permissions)

    // Audio DSP library
    implementation(libs.tarsos.dsp.core)
    implementation(libs.tarsos.dsp.jvm)

    // Horologist
    implementation(libs.horologist.compose.layout)
    implementation(libs.horologist.compose.material)

    // Tiles
    implementation(libs.androidx.tiles.material)
    implementation(libs.androidx.tiles)
    implementation(libs.horologist.tiles)

    // Datastore
    implementation(libs.androidx.datastore)

    implementation(libs.androidx.core.splashscreen)

    // Tests
    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
}
