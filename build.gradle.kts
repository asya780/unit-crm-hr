import com.github.gradle.node.npm.proxy.ProxySettings
import com.github.gradle.node.npm.task.NpmTask

val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val h2_version: String by project
val sqlite_version: String by project
val koin_version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.11"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    id("com.github.node-gradle.node") version "7.0.2"
    idea
}

group = "ua.shvets"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}


val useLocalNodeJs: Boolean = when(project.hasProperty("useLocalNodeJs")) {
    true -> project.property("useLocalNodeJs") as String
    else -> "true" // by default using local installed node.js
}.toBoolean()

node {
    download.set(!useLocalNodeJs)
    version.set("18.12.1")
    distBaseUrl.set("https://nodejs.org/dist")
    npmInstallCommand.set("install")
    workDir.set(file("${project.projectDir}/.gradle/nodejs"))
    npmWorkDir.set(file("${project.projectDir}/.gradle/npm"))
    yarnWorkDir.set(file("${project.projectDir}/.gradle/yarn"))
    nodeProxySettings.set(ProxySettings.SMART)
    nodeProjectDir.set(file("${project.projectDir}/web-app"))
}

tasks.register<NpmTask>("buildUi") {
    description = "Build UNIT CRM System UI application."
    dependsOn(tasks.npmInstall)
    val commandList = mutableListOf("run")
    commandList.add("build")
    npmCommand.set(commandList)
    args.set(listOf("--"))
    inputs.dir(projectDir.resolve("web-app/src"))
    outputs.dir(projectDir.resolve("web-app/dist"))
}

tasks.named("classes") {
    dependsOn("buildUi")
}


dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("org.xerial:sqlite-jdbc:$sqlite_version")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-resources-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
}

idea {
    module {
        isDownloadJavadoc = true
    }
}