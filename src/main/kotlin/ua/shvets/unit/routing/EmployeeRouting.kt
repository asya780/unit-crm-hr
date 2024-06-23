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
import ua.shvets.unit.domain.Employee
import ua.shvets.unit.repository.EmployeeRepository

fun Routing.employeeRouting() {
    val employeeRepository: EmployeeRepository by inject<EmployeeRepository>()
    route("/employee") {
        get {
            val employees = employeeRepository.all()
            call.respond(HttpStatusCode.OK, employees)
        }
        post {
            val employee = call.receive<Employee>()
            employeeRepository.add(employee)
            call.respond(HttpStatusCode.Created)
        }
        post("/{id}") {
            val employeeId = call.parameters["id"]?.toInt()
            if (employeeId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val updatedEmployee = call.receive<Employee>()
            employeeRepository.update(updatedEmployee)
            call.respond(HttpStatusCode.OK)
        }
        delete("/{id}") {
            val employeeId = call.parameters["id"]?.toInt()
            val response = if (employeeId != null) {
                try {
                    employeeRepository.remove(employeeId)
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
            val employeeId = call.parameters["id"]?.toInt()
            val response = if (employeeId != null) {
                employeeRepository.findById(employeeId)
                HttpStatusCode.OK
            } else {
                HttpStatusCode.BadRequest
            }
            call.respond(response)
        }
    }
}