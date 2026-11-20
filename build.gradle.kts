plugins {
    kotlin("multiplatform") version "2.4.10" apply false
    kotlin("plugin.serialization") version "2.4.10" apply false
    kotlin("plugin.compose") version "2.4.10" apply false
    id("org.jetbrains.compose") version "1.12.0" apply false
    id("com.android.application") version "9.1.0" apply false
    id("com.android.kotlin.multiplatform.library") version "9.1.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "14.2.0" apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

val detektCli: Configuration by configurations.creating {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin") useVersion("2.0.21")
    }
}

dependencies {
    detektCli("io.gitlab.arturbosch.detekt:detekt-cli:1.23.8")
}

tasks.register<JavaExec>("detekt") {
    group = "verification"
    mainClass.set("io.gitlab.arturbosch.detekt.cli.Main")
    classpath = detektCli
    args(
        "--input", "${file("shared/src")},${file("composeApp/src")}",
        "--config", file("detekt.yml").path,
        "--build-upon-default-config",
    )
}
