package ua.shvets.unit.plugins

import io.ktor.server.application.Application
import io.ktor.server.http.content.singlePageApplication
import io.ktor.server.http.content.vue
import io.ktor.server.routing.routing

fun Application.configureSinglePageApplication() {
    routing {
        singlePageApplication {
            vue("web-app/dist")
        }
    }

}