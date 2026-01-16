plugins {
    id("com.android.library")
    kotlin("android")
    alias(libs.plugins.hilt)
    id("org.jetbrains.kotlin.kapt")
}

android {
    compileSdk = libs.versions.compile.sdk.version.get().toInt()
    namespace = "com.smartfilerenamer.core.data"

    defaultConfig {
        minSdk = libs.versions.min.sdk.version.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.documentfile)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
}
