package ua.shvets.unit.db

import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import ua.shvets.unit.db.DepartmentTable.defaultExpression

object PersonalInformationTable : IntIdTable("PersonalInformation") {
    val registrationAddress = varchar("registration_address", 255)
    val personalNumber = varchar("personal_number", 255)
    val address = varchar("address", 20)
    val creationTime = datetime("creation_time").defaultExpression(CurrentDateTime)
    val lastUpdateTime = datetime("last_update_time").defaultExpression(CurrentDateTime)
}

class PersonalInformationDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PersonalInformationDAO>(PersonalInformationTable)

    var registrationAddress by PersonalInformationTable.registrationAddress
    var personalNumber by PersonalInformationTable.personalNumber
    var address by PersonalInformationTable.address
    var creationTime by PersonalInformationTable.creationTime
    var lastUpdateTime by PersonalInformationTable.lastUpdateTime
}