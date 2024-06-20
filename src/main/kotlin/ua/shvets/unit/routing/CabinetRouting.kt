package ua.shvets.unit.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import ua.shvets.unit.domain.Cabinet
import ua.shvets.unit.repository.CabinetRepository

fun Routing.cabinetRouting() {
    val cabinetRepository: CabinetRepository by inject<CabinetRepository>()
    route("/cabinet") {
        get {
            val cabinets = cabinetRepository.all()
            call.respond(HttpStatusCode.OK, cabinets)
        }
        post {
            val cabinet = call.receive<Cabinet>()
            cabinetRepository.add(cabinet)
        }
    }
}