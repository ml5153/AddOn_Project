plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.devsudal.presenter.app"
    compileSdk = 34
    buildToolsVersion = "31.0.0"

    defaultConfig {
        applicationId = "com.devsudal.presenter.app"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    // default
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // base
    implementation(libs.androidx.appcompat)


    /***
     * Module(PointHome SDK)
     */
    implementation(project(":Library-SDK-PH"))


    /**
     * 사용 유무를 앱사에서 결정함
     * 앱사 액션 행위

      1. AddOn 모듈 참조
       Library-SDK-AddOn:Library-SDK-AddOn-Buzzvil
      2. 직접 참조
       com.buzzvil:buzzvil-bom:5.17.1"
       com.buzzvil:buzzvil-sdk
      3. 참조 x
        SDK 내부에 버즈빌 애드온 기능이 자동 OFF 됨

     */
    // 1. AddOn 모듈 참조
    implementation(project(":Library-SDK-AddOn:Library-SDK-AddOn-Buzzvil"))
    implementation(project(":Library-SDK-AddOn:Library-SDK-AddOn-LockScreen"))
    // 2. 직접참조
    api(platform("com.buzzvil:buzzvil-bom:5.17.1"))
    implementation("com.buzzvil:buzzvil-sdk")
//    implementation ("com.buzzvil:buzzad-benefit:3.54.+") // BuzzAd v3 SDK 라이브러리

    implementation(project(":Library-SDK-LOG"))

}