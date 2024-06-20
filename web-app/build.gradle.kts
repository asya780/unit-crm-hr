import com.github.gradle.node.npm.proxy.ProxySettings
import com.github.gradle.node.npm.task.NpmTask

plugins {
    id("com.github.node-gradle.node") version "3.5.0"
}

val useLocalNodeJs: Boolean = when(project.hasProperty("useLocalNodeJs")) {
    true -> project.property("useLocalNodeJs") as String
    else -> "true" // by default using local installed node.js
}.toBoolean()

val buildMode: String = when (project.hasProperty("mode")) {
    true -> project.property("mode") as String
    else -> "dev"
}

node {
    download.set(!useLocalNodeJs)
    version.set("18.12.1")
    distBaseUrl.set("https://nodejs.org/dist")
    npmInstallCommand.set("install")
    workDir.set(file("${project.projectDir}/.gradle/nodejs"))
    npmWorkDir.set(file("${project.projectDir}/.gradle/npm"))
    yarnWorkDir.set(file("${project.projectDir}/.gradle/yarn"))
    nodeProxySettings.set(ProxySettings.SMART)
}

tasks.register<NpmTask>("buildUi") {
    description = "Build UNIT CRM UI application."
    dependsOn(tasks.npmInstall)
    val commandList = mutableListOf("run")
    when (buildMode) {
        "production" -> commandList.add("build")
        else -> commandList.add("buildDev")
    }
    npmCommand.set(commandList)
    args.set(listOf("--", "--out-dir", "$buildDir"))
    inputs.dir(project(":web-app").projectDir.resolve("src"))
    outputs.dir(project(":web-app").buildDir)
}