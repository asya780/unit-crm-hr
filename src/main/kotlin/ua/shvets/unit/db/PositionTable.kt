package ua.shvets.unit.db

import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object PositionTable : IntIdTable("Position") {
    val name = varchar("name", 50)
    val salary = integer("salary")
    val creationTime = timestamp("creation_time").default(Instant.fromEpochMilliseconds(System.currentTimeMillis()))
    val lastUpdateTime =
        timestamp("last_update_time").default(Instant.fromEpochMilliseconds(System.currentTimeMillis()))
}

class PositionDAO(id: EntityID<Int>) : IntEntity(id) {
    val name by PositionTable.name
    val salary by PositionTable.salary
    val creationTime by PositionTable.creationTime
    val lastUpdateTime by PositionTable.lastUpdateTime
}