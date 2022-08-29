val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val exposedVersion: String by project
val postgresVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
    id("io.ktor.plugin") version "2.1.0"
    //Kotlin Serialization
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
}

group = "ru.myback01"
version = "0.0.1"
application {
    mainClass.set("ru.myback01.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    //Kotlin Serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    //Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    //Postgres
    implementation("org.postgresql:postgresql:$postgresVersion")

}