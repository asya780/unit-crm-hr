package ua.shvets.unit.db

import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object PersonalInformationTable : IntIdTable("PersonalInformation") {
    val registrationAddress = varchar("registration_address", 255)
    val personalNumber = varchar("personal_number", 255)
    val address = varchar("address", 20)
    val creationTime = timestamp("creation_time").default(Instant.fromEpochMilliseconds(System.currentTimeMillis()))
    val lastUpdateTime =
        timestamp("last_update_time").default(Instant.fromEpochMilliseconds(System.currentTimeMillis()))
}

class PersonalInformationDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PersonalInformationDAO>(PersonalInformationTable)

    val registrationAddress by PersonalInformationTable.registrationAddress
    val personalNumber by PersonalInformationTable.personalNumber
    val address by PersonalInformationTable.address
    val creationTime by PersonalInformationTable.creationTime
    val lastUpdateTime by PersonalInformationTable.lastUpdateTime
}