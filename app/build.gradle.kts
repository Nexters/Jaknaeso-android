import java.util.*

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
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
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "LIBRARY_PACKAGE_NAME", "\"com.jaknaeso.app\"")
        buildConfigField("String", "BASE_URL", localProperties.getProperty("BASE_URL"))
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(libs.datastore)
    implementation(libs.bundles.basic)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.androidx.hilt)
    implementation(libs.bundles.ui)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.network)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
