package ua.shvets.unit.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
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
            call.respond(HttpStatusCode.Created)
        }
        post("/{id}") {
            val cabinetId = call.parameters["id"]?.toInt()
            if (cabinetId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val updatedCabinet = call.receive<Cabinet>()
            cabinetRepository.update(updatedCabinet)
            call.respond(HttpStatusCode.OK)
        }
        delete("/{id}") {
            val cabinetId = call.parameters["id"]?.toInt()
            val response = if (cabinetId != null) {
                try {
                    cabinetRepository.remove(cabinetId)
                    HttpStatusCode.OK
                } catch (e: Throwable) {
                    HttpStatusCode.BadGateway
                }
            } else {
                HttpStatusCode.BadRequest
            }
            call.respond(response)
        }
        get("/{id}") {
            val cabinetId = call.parameters["id"]?.toInt()
            val response = if (cabinetId != null) {
                cabinetRepository.findById(cabinetId)
                HttpStatusCode.OK
            } else {
                HttpStatusCode.BadRequest
            }
            call.respond(response)
        }
    }
}