plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.alonsorafael.quizapp_android_firebase"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.alonsorafael.quizapp_android_firebase"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    
    // Configuração específica para kapt
    kapt {
        javacOptions {
            option("-source", "11")
            option("-target", "11")
        }
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    buildFeatures {
        compose = true
    }
    
    // Configuração específica do Compose Compiler
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
}

dependencies {
    // Android Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    
    // Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.realtime.database)
    implementation(libs.firebase.storage)
    
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    
    // Navigation
    implementation(libs.navigation.compose)
    
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    
    // Coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.play.services)
    
    // DataStore
    implementation(libs.data.store)
    
    // Coil
    implementation(libs.coil.compose)
    
    // Gson
    implementation(libs.gson)
    
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}