package ua.shvets.unit.db

import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import ua.shvets.unit.domain.Cabinet
import ua.shvets.unit.domain.Department
import ua.shvets.unit.domain.Employee
import ua.shvets.unit.domain.Position

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: EmployeeDAO) = Employee(
    dao.id.value,
    dao.name,
    dao.surname,
    dao.middleName,
    dao.birthDate,
    dao.multiplier,
    dao.active,
    dao.dismissalDate,
    dao.creationTime.toLocalDateTime(TimeZone.UTC),
    dao.lastUpdateTime.toLocalDateTime(TimeZone.UTC),
)

fun daoToModel(dao: CabinetDAO) = Cabinet(
    dao.id.value,
    dao.name,
    dao.floor,
    dao.creationTime.toLocalDateTime(TimeZone.UTC),
    dao.lastUpdateTime.toLocalDateTime(TimeZone.UTC),
)

fun daoToModel(dao: PositionDAO) = Position(
    dao.id.value,
    dao.name,
    dao.salary,
    dao.creationTime.toLocalDateTime(TimeZone.UTC),
    dao.lastUpdateTime.toLocalDateTime(TimeZone.UTC),
)

fun daoToModel(dao: DepartmentDAO) = Department(
    dao.id.value,
    dao.name,
    dao.creationTime.toLocalDateTime(TimeZone.UTC),
    dao.lastUpdateTime.toLocalDateTime(TimeZone.UTC),
)