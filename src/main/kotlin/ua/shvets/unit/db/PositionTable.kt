package ua.shvets.unit.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object PositionTable : IntIdTable("Position") {
    val name = varchar("name", 50)
    val salary = integer("salary")
    val creationTime = datetime("creation_time").defaultExpression(CurrentDateTime)
    val lastUpdateTime = datetime("last_update_time").defaultExpression(CurrentDateTime)
}

class PositionDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PositionDAO>(PositionTable)

    var name by PositionTable.name
    var salary by PositionTable.salary
    var creationTime by PositionTable.creationTime
    var lastUpdateTime by PositionTable.lastUpdateTime
}