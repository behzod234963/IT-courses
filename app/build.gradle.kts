plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt.plugin)
    alias(libs.plugins.kapt.compiler)
    alias(libs.plugins.safe.args.plugin)
}

android {
    namespace = "com.mr.anonym.it_courses"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mr.anonym.it_courses"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project("::data"))
    implementation(project("::domain"))

    implementation(libs.kotlin.coroutines)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.adapter.delegates)
    implementation(libs.adapter.delegates.viewBinding)
    implementation(libs.retrofit2)
    implementation(libs.gsonConverter)
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.coroutines)
    kapt(libs.room.kapt.compiler)
    implementation(libs.androidx.dataStore)
    implementation(libs.glide)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
}