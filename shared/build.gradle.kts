plugins {
    kotlin("multiplatform")
    id("com.android.kotlin.multiplatform.library")
}

kotlin {
    jvmToolchain(21)

    jvm()

    androidLibrary {
        namespace = "br.ufrn.agendafacil.model"
        compileSdk = 37
        minSdk = 24
    }

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
