package ua.shvets.unit.db

import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object CabinetTable : IntIdTable("Cabinet") {
    val name = varchar("name", 50)
    val floor = integer("floor")
    val creationTime = timestamp("creation_time").default(Instant.fromEpochMilliseconds(System.currentTimeMillis()))
    val lastUpdateTime =
        timestamp("last_update_time").default(Instant.fromEpochMilliseconds(System.currentTimeMillis()))
}

class CabinetDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CabinetDAO>(CabinetTable)

    val name by CabinetTable.name
    val floor by CabinetTable.floor
    val creationTime by CabinetTable.creationTime
    val lastUpdateTime by CabinetTable.lastUpdateTime
}