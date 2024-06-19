package ua.shvets.unit.db

import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object VacationTable : IntIdTable("Vacation") {
    val start = date("start")
    val end = date("end")
    val approved = bool("approved")
    val creationTime = timestamp("creation_time").default(Instant.fromEpochMilliseconds(System.currentTimeMillis()))
    val lastUpdateTime =
        timestamp("last_update_time").default(Instant.fromEpochMilliseconds(System.currentTimeMillis()))
}

class VacationDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<VacationDAO>(VacationTable)

    val start by VacationTable.start
    val end by VacationTable.end
    val approved by VacationTable.approved
    val creationTime by VacationTable.creationTime
    val lastUpdateTime by VacationTable.lastUpdateTime
}