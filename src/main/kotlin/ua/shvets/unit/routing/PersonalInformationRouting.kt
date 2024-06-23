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
import ua.shvets.unit.domain.PersonalInformation
import ua.shvets.unit.repository.PersonalInformationRepository

fun Routing.personalInformationRouting() {
    val personalInformationRepository: PersonalInformationRepository by inject<PersonalInformationRepository>()
    route("/personalInformation") {
        get {
            val personalInformations = personalInformationRepository.all()
            call.respond(HttpStatusCode.OK, personalInformations)
        }
        post {
            val personalInformation = call.receive<PersonalInformation>()
            personalInformationRepository.add(personalInformation)
            call.respond(HttpStatusCode.Created)
        }
        post("/{id}") {
            val personalInformationId = call.parameters["id"]?.toInt()
            if (personalInformationId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val updatedPersonalInformation = call.receive<PersonalInformation>()
            personalInformationRepository.update(updatedPersonalInformation)
            call.respond(HttpStatusCode.OK)
        }
        delete("/{id}") {
            val personalInformationId = call.parameters["id"]?.toInt()
            val response = if (personalInformationId != null) {
                try {
                    personalInformationRepository.remove(personalInformationId)
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
            val personalInformationId = call.parameters["id"]?.toInt()
            val response = if (personalInformationId != null) {
                personalInformationRepository.findById(personalInformationId)
                HttpStatusCode.OK
            } else {
                HttpStatusCode.BadRequest
            }
            call.respond(response)
        }
    }
}