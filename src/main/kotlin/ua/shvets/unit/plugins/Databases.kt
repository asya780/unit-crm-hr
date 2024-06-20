package ua.shvets.unit.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.plugin.KoinApplicationStarted
import org.koin.ktor.plugin.KoinApplicationStopPreparing
import org.koin.ktor.plugin.KoinApplicationStopped
import ua.shvets.unit.db.CabinetTable
import ua.shvets.unit.db.DepartmentTable
import ua.shvets.unit.db.EmployeeTable
import ua.shvets.unit.db.PersonalInformationTable
import ua.shvets.unit.db.PositionTable
import ua.shvets.unit.db.VacationTable
import ua.shvets.unit.repository.CabinetRepository
import ua.shvets.unit.repository.DepartmentRepository
import ua.shvets.unit.repository.EmployeeRepository
import ua.shvets.unit.repository.PersonalInformationRepository
import ua.shvets.unit.repository.PositionRepository
import ua.shvets.unit.repository.VacationRepository
import ua.shvets.unit.repository.sqlite.SqliteCabinetRepository
import ua.shvets.unit.repository.sqlite.SqliteDepartmentRepository
import ua.shvets.unit.repository.sqlite.SqliteEmployeeRepository
import ua.shvets.unit.repository.sqlite.SqlitePersonalInformationRepository
import ua.shvets.unit.repository.sqlite.SqlitePositionRepository
import ua.shvets.unit.repository.sqlite.SqliteVacationRepository

fun Application.configureDatabases(): Module {
    val database = Database.connect(
        url = "jdbc:sqlite:storage.db",
        driver = "org.sqlite.JDBC",
    )
    transaction(database) {
        SchemaUtils.create(DepartmentTable)
        SchemaUtils.create(PersonalInformationTable)
        SchemaUtils.create(PositionTable)
        SchemaUtils.create(CabinetTable)
        SchemaUtils.create(EmployeeTable)
        SchemaUtils.create(VacationTable)
    }
    routing {
        // Create user
//        post("/users") {
//            val user = call.receive<ExposedUser>()
//            val id = userService.create(user)
//            call.respond(HttpStatusCode.Created, id)
//        }
//
//            // Read user
//        get("/users/{id}") {
//            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
//            val user = userService.read(id)
//            if (user != null) {
//                call.respond(HttpStatusCode.OK, user)
//            } else {
//                call.respond(HttpStatusCode.NotFound)
//            }
//        }
//
//            // Update user
//        put("/users/{id}") {
//            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
//            val user = call.receive<ExposedUser>()
//            userService.update(id, user)
//            call.respond(HttpStatusCode.OK)
//        }
//
//            // Delete user
//        delete("/users/{id}") {
//            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
//            userService.delete(id)
//            call.respond(HttpStatusCode.OK)
//        }
    }
    val dbModule = module {
        single { database }
        single<PositionRepository> { SqlitePositionRepository() }
        single<EmployeeRepository> { SqliteEmployeeRepository() }
        single<DepartmentRepository> { SqliteDepartmentRepository() }
        single<VacationRepository> { SqliteVacationRepository() }
        single<CabinetRepository> { SqliteCabinetRepository() }
        single<PersonalInformationRepository> { SqlitePersonalInformationRepository() }
    }
    return dbModule
}
