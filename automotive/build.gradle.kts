plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.polestar.contextaware"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.polestar.contextaware"
        minSdk = 28
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Kotlin Coroutines (ESSENTIAL for suspend functions and Flow)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Android Lifecycle & ViewModel (ESSENTIAL for the MVVM pattern)
    // We rely on these libraries in DashboardViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Optional: Dependency Injection (If we moved from manual wiring in Main.kt)
    // For example, if using Koin:
    // implementation("io.insert-koin:koin-android:3.4.0")

    // AAOS specific dependencies (e.g., Car API bindings) would be added here in a real project
    implementation(libs.androidx.app)
}