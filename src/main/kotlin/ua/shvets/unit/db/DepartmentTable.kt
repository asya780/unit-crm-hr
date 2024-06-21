package ua.shvets.unit.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object DepartmentTable : IntIdTable("Department") {
    val name = varchar("name", 50)
    val creationTime = datetime("creation_time").defaultExpression(CurrentDateTime)
    val lastUpdateTime = datetime("last_update_time").defaultExpression(CurrentDateTime)
}

class DepartmentDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DepartmentDAO>(DepartmentTable)


    var name by DepartmentTable.name
    var creationTime by DepartmentTable.creationTime
    var lastUpdateTime by DepartmentTable.lastUpdateTime
}