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
