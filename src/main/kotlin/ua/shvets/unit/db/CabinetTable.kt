package ua.shvets.unit.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ua.shvets.unit.db.DepartmentTable.uniqueIndex

object CabinetTable : IntIdTable("Cabinet") {
    val name = varchar("name", 50).uniqueIndex()
    val floor = integer("floor")
    val creationTime = datetime("creation_time").defaultExpression(CurrentDateTime)
    val lastUpdateTime = datetime("last_update_time").defaultExpression(CurrentDateTime)
}


class CabinetDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CabinetDAO>(CabinetTable)

    var name by CabinetTable.name
    var floor by CabinetTable.floor
    var creationTime by CabinetTable.creationTime
    var lastUpdateTime by CabinetTable.lastUpdateTime
}