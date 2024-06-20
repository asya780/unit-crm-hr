package ua.shvets.unit.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import ua.shvets.unit.domain.Cabinet
import ua.shvets.unit.domain.Department
import ua.shvets.unit.domain.Employee
import ua.shvets.unit.domain.PersonalInformation
import ua.shvets.unit.domain.Position
import ua.shvets.unit.domain.Vacation

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
    dao.creationTime,
    dao.lastUpdateTime,
    daoToModel(dao.position),
    daoToModel(dao.personalInformation),
    daoToModel(dao.department),
    daoToModel(dao.cabinet)
)

fun daoToModel(dao: CabinetDAO) = Cabinet(
    dao.id.value,
    dao.name,
    dao.floor,
    dao.creationTime,
    dao.lastUpdateTime,
)

fun daoToModel(dao: PositionDAO) = Position(
    dao.id.value,
    dao.name,
    dao.salary,
    dao.creationTime,
    dao.lastUpdateTime,
)

fun daoToModel(dao: DepartmentDAO) = Department(
    dao.id.value,
    dao.name,
    dao.creationTime,
    dao.lastUpdateTime,
)

fun daoToModel(dao: PersonalInformationDAO) = PersonalInformation(
    dao.id.value,
    dao.registrationAddress,
    dao.personalNumber,
    dao.address,
    dao.creationTime,
    dao.lastUpdateTime,
)

fun daoToModel(dao: VacationDAO) = Vacation(
    dao.id.value,
    dao.start,
    dao.end,
    dao.approved,
    dao.creationTime,
    dao.lastUpdateTime,
    daoToModel(dao.employee),
)