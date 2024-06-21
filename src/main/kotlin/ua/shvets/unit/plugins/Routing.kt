package ua.shvets.unit.plugins

import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.http.content.staticFiles
import io.ktor.server.resources.Resources
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import ua.shvets.unit.routing.cabinetRouting
import ua.shvets.unit.routing.departmentRouting
import ua.shvets.unit.routing.employeeRouting
import ua.shvets.unit.routing.personalInformationRouting
import ua.shvets.unit.routing.positionRouting
import ua.shvets.unit.routing.vacationRouting
import java.io.File

fun Application.configureRouting() {
    install(Resources)
    routing {
        cabinetRouting()
        departmentRouting()
        employeeRouting()
        personalInformationRouting()
        positionRouting()
        vacationRouting()
        get<Articles> { article ->
            // Get all articles ...
            call.respond("List of articles sorted starting from ${article.sort}")
        }
    }
}

@Serializable
@Resource("/articles")
class Articles(val sort: String? = "new")
