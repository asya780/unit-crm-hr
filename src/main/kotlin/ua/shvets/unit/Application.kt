package ua.shvets.unit

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
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
    val dbModule = configureDatabases()
    configureMonitoring()
    configureHTTP()
    configureRouting()
    install(Koin) {
        slf4jLogger()
        modules(
            appModule,
            dbModule
        )
    }
}

