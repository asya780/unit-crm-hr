package ua.shvets.unit.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.log
import io.ktor.server.routing.routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
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
import java.nio.file.Files
import java.nio.file.Paths

fun Application.configureDatabases(): Module {
    val database = Database.connect(
        url = "jdbc:sqlite:storage.db",
        driver = "org.sqlite.JDBC",
    )
    val demoData = Files.readString(Paths.get(Application::class.java.getResource("/sql/sqlite/demo_records.sql").toURI()))
    transaction(database) {
        SchemaUtils.create(DepartmentTable)
        SchemaUtils.create(PersonalInformationTable)
        SchemaUtils.create(PositionTable)
        SchemaUtils.create(CabinetTable)
        SchemaUtils.create(EmployeeTable)
        SchemaUtils.create(VacationTable)
        var shouldRun = false
        val statement = StringBuilder()
        try {
            for (c in demoData) {
                statement.append(c)
                if (c == ';')
                    shouldRun = true
                if (shouldRun) {
                    shouldRun = false
                    exec(statement.toString())
                    statement.clear()
                }
            }
        } catch (e: Throwable) {
            log.warn("There is error in preparing database.", e)
        }
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
