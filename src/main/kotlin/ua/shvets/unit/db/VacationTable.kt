package ua.shvets.unit.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object VacationTable : IntIdTable("Vacation") {
    val start = date("start")
    val end = date("end")
    val approved = bool("approved")
    val creationTime = datetime("creation_time").defaultExpression(CurrentDateTime)
    val lastUpdateTime = datetime("last_update_time").defaultExpression(CurrentDateTime)
    val employee = reference("employee", EmployeeTable)
}

class VacationDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<VacationDAO>(VacationTable)

    var start by VacationTable.start
    var end by VacationTable.end
    var approved by VacationTable.approved
    var creationTime by VacationTable.creationTime
    var lastUpdateTime by VacationTable.lastUpdateTime
    var employee by EmployeeDAO referencedOn VacationTable.employee
}