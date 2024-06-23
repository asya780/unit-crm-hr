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
import ua.shvets.unit.domain.Position
import ua.shvets.unit.domain.PersonalInformation
import ua.shvets.unit.repository.PositionRepository
import ua.shvets.unit.repository.PersonalInformationRepository

fun Routing.positionRouting() {
    val positionRepository: PositionRepository by inject<PositionRepository>()
    route("/position") {
        get {
            val positions = positionRepository.all()
            call.respond(HttpStatusCode.OK, positions)
        }
        post {
            val position = call.receive<Position>()
            positionRepository.add(position)
            call.respond(HttpStatusCode.Created)
        }
        post("/{id}") {
            val positionId = call.parameters["id"]?.toInt()
            if (positionId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val updatedPosition = call.receive<Position>()
            positionRepository.update(updatedPosition)
            call.respond(HttpStatusCode.OK)
        }
        delete("/{id}") {
            val positionId = call.parameters["id"]?.toInt()
            val response = if (positionId != null) {
                try {
                    positionRepository.remove(positionId)
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
            val positionId = call.parameters["id"]?.toInt()
            val response = if (positionId != null) {
                positionRepository.findById(positionId)
                HttpStatusCode.OK
            } else {
                HttpStatusCode.BadRequest
            }
            call.respond(response)
        }
    }}