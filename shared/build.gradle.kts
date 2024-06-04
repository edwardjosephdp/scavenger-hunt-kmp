import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.runtime)
            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.android.driver)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.native.driver)
        }
    }
}

/* NOTE: Kotlin Multiplatform <-> Android Gradle Plugin compatibility issue MIN=7.0 MAX=8.2
*  To suppress this message add 'kotlin.mpp.androidGradlePluginCompatibility.nowarn=true' to your gradle.properties
* */
buildkonfig {
    packageName = "com.oddguild.scavengerai"
    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "BASE_URL", "https://generativelanguage.googleapis.com")
        buildConfigField(FieldSpec.Type.STRING, "GEMINI_API_KEY", "AIzaSyCw-NC9qkjF2abvzIdIfYq4SRY26jz0u2s")
    }
}

android {
    namespace = "com.oddguild.scavengerai"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
