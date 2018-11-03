import com.android.builder.model.ApiVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.bulwinkel.slemusman"
        setMinSdkVersion (23)
        setTargetSdkVersion (28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(Deps.kotlin_stdlib_jdk7)

    implementation(Deps.androidx_appcompat)
    implementation(Deps.androidx_constraintlayout)
    implementation(Deps.androidx_core_ktx)
    implementation(Deps.androidx_fragment_ktx)
    implementation(Deps.android_arch_work_runtime_ktx)

    testImplementation("junit:junit:4.12")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}
