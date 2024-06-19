package ua.shvets.unit.db

import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object EmployeeTable : IntIdTable("Employee") {
    val name = varchar("name", 50)
    val surname = varchar("surname", 50)
    val middleName = varchar("middle_name", 255)
    val birthDate = date("birth_date")
    val multiplier = float("multiplier")
    val active = bool("active")
    val dismissalDate = date("dismissal_date")
    val creationTime = timestamp("creation_time").default(Instant.fromEpochMilliseconds(System.currentTimeMillis()))
    val lastUpdateTime =
        timestamp("last_update_time").default(Instant.fromEpochMilliseconds(System.currentTimeMillis()))
    val position = reference("position_id", PositionTable)
    val personalInformation = reference("personal_information_id", PersonalInformationTable)
    val department = reference("department", DepartmentTable)
    val cabinet = reference("cabinet", CabinetTable)
}

class EmployeeDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EmployeeDAO>(EmployeeTable)

    var name by EmployeeTable.name
    var surname by EmployeeTable.surname
    var middleName by EmployeeTable.middleName
    var birthDate by EmployeeTable.birthDate
    var multiplier by EmployeeTable.multiplier
    var active by EmployeeTable.active
    var dismissalDate by EmployeeTable.dismissalDate
    var creationTime by EmployeeTable.creationTime
    var lastUpdateTime by EmployeeTable.lastUpdateTime
    val position by PositionDAO referencedOn EmployeeTable.position
    val personalInformation by PersonalInformationDAO referencedOn EmployeeTable.personalInformation
    val department by DepartmentDAO referencedOn EmployeeTable.department
    val cabinet by CabinetDAO referencedOn EmployeeTable.cabinet
}
