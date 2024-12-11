plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = this.name
            isStatic = true
        }
    }

    sourceSets {
        iosMain.dependencies {
            implementation(project(":notifykt-common"))
            implementation(libs.kotlinx.coroutine)
        }
    }
}

android {
    namespace = "io.github.jerrya.notifykt"
    compileSdk = 34
    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
