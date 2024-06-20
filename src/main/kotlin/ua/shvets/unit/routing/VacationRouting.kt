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
import ua.shvets.unit.domain.Vacation
import ua.shvets.unit.repository.VacationRepository

fun Routing.vacationRouting() {
    val vacationRepository: VacationRepository by inject<VacationRepository>()
    route("/vacation") {
        get {
            val vacations = vacationRepository.all()
            call.respond(HttpStatusCode.OK, vacations)
        }
        post {
            val vacation = call.receive<Vacation>()
            vacationRepository.add(vacation)
            call.respond(HttpStatusCode.Created)
        }
        post("/{id}") {
            val vacationId = call.parameters["id"]?.toInt()
            if (vacationId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val updatedVacation = call.receive<Vacation>()
            vacationRepository.update(updatedVacation)
        }
        delete("/{id}") {
            val vacationId = call.parameters["id"]?.toInt()
            val response = if (vacationId != null) {
                try {
                    vacationRepository.remove(vacationId)
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
            val vacationId = call.parameters["id"]?.toInt()
            val response = if (vacationId != null) {
                vacationRepository.findById(vacationId)
                HttpStatusCode.OK
            } else {
                HttpStatusCode.BadRequest
            }
            call.respond(response)
        }
    }
}