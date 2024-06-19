package ua.shvets.unit.db

import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object DepartmentTable : IntIdTable("Department") {
    val name = varchar("name", 50)
    val creationTime = timestamp("creation_time").default(Instant.fromEpochMilliseconds(System.currentTimeMillis()))
    val lastUpdateTime =
        timestamp("last_update_time").default(Instant.fromEpochMilliseconds(System.currentTimeMillis()))
}

class DepartmentDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DepartmentDAO>(DepartmentTable)


    val name by DepartmentTable.name
    val creationTime by DepartmentTable.creationTime
    val lastUpdateTime by DepartmentTable.lastUpdateTime
}