package ua.shvets.unit.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*

fun Application.configureHTTP() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowHeader(HttpHeaders.ContentType)
        allowHost("0.0.0.0:8080")
        allowHost("0.0.0.0:3000")
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }
}
