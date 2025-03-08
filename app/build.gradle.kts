import java.util.*

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.services)
    alias(libs.plugins.hilt.android.gradle)
    kotlin("kapt")
    id("com.google.android.gms.oss-licenses-plugin")
}

val localProperties = Properties().apply {
    load(project.rootProject.file("./local.properties").inputStream())
}


android {
    namespace = "com.jaknaeso.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jaknaeso.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 15
        versionName = "1.0.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "LIBRARY_PACKAGE_NAME", "\"com.jaknaeso.app\"")
        buildConfigField("String", "BASE_URL", localProperties.getProperty("BASE_URL"))
        buildConfigField("String", "NATIVE_APP_KEY", localProperties.getProperty("NATIVE_APP_KEY"))
        manifestPlaceholders["REDIRECTION_PATH"] = localProperties["REDIRECTION_PATH"] as String
    }
    signingConfigs {
        create("release") {
            keyAlias = localProperties.getProperty("KEY_ALIAS")
            keyPassword = localProperties.getProperty("KEY_PASSWORD")
            storeFile = file("./release.keystore")
            storePassword = localProperties.getProperty("STORE_PASSWORD")
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {
    implementation(libs.splash.screen)
    implementation(libs.airbnb.lottie.compose)
    implementation(libs.kakao.all)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.analytics)
    implementation(libs.kotlinx.collections.immutable)
    kapt(libs.hilt.viewmodel)
    kapt(libs.hilt.android.compiler)
    implementation(libs.open.licenses)
    implementation(libs.datastore)
    implementation(libs.bundles.basic)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.ui)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.network)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
