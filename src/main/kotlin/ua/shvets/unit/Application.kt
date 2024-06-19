package ua.shvets.unit

import io.ktor.server.application.Application
import ua.shvets.unit.plugins.configureDatabases
import ua.shvets.unit.plugins.configureHTTP
import ua.shvets.unit.plugins.configureMonitoring
import ua.shvets.unit.plugins.configureRouting
import ua.shvets.unit.plugins.configureSerialization

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureMonitoring()
    configureHTTP()
    configureRouting()
}
