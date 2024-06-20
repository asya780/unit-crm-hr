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
import ua.shvets.unit.domain.Department
import ua.shvets.unit.repository.DepartmentRepository

fun Routing.departmentRouting() {
    val departmentRepository: DepartmentRepository by inject<DepartmentRepository>()
    route("/department") {
        get {
            val departments = departmentRepository.all()
            call.respond(HttpStatusCode.OK, departments)
        }
        post {
            val department = call.receive<Department>()
            departmentRepository.add(department)
            call.respond(HttpStatusCode.Created)
        }
        post("/{id}") {
            val departmentId = call.parameters["id"]?.toInt()
            if (departmentId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val updatedDepartment = call.receive<Department>()
            departmentRepository.update(updatedDepartment)
        }
        delete("/{id}") {
            val departmentId = call.parameters["id"]?.toInt()
            val response = if (departmentId != null) {
                try {
                    departmentRepository.remove(departmentId)
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
            val departmentId = call.parameters["id"]?.toInt()
            val response = if (departmentId != null) {
                departmentRepository.findById(departmentId)
                HttpStatusCode.OK
            } else {
                HttpStatusCode.BadRequest
            }
            call.respond(response)
        }
    }
}