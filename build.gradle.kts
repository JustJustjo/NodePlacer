import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    kotlin("jvm") version "1.7.20"
    id("com.github.gmazzo.buildconfig") version "3.0.0"
    id("org.openjfx.javafxplugin") version "0.0.13"
}
buildConfig {
    buildConfigField ("long", "BUILD_TIME", "${System.currentTimeMillis()}L")
}

repositories {
    mavenCentral()
    maven { setUrl("https://frcmaven.wpi.edu/artifactory/release/")}
}
javafx {
    version = "19"
    modules = "javafx.controls,javafx.fxml".split(",").toMutableList()
}
application {
    mainClass.set("com.kilk.Main")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("com.google.code.gson:gson:2.8.9")
}

java {
    withSourcesJar()
    withJavadocJar()
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}