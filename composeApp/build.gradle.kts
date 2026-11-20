plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
    id("com.android.application")
}

kotlin {
    jvmToolchain(21)

    jvm()
    androidTarget()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation("org.jetbrains.compose.ui:ui-tooling-preview:1.12.0")
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.2")
            implementation("org.jetbrains.compose.material3.adaptive:adaptive:1.2.0")
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
        androidMain.dependencies {
            implementation("androidx.activity:activity-compose:1.9.3")
            implementation("org.jetbrains.compose.ui:ui-tooling:1.12.0")
        }
    }
}

android {
    namespace = "br.ufrn.agendafacil"
    compileSdk = 37

    defaultConfig {
        applicationId = "br.ufrn.agendafacil"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

compose.desktop {
    application {
        mainClass = "br.ufrn.agendafacil.MainKt"
    }
}
