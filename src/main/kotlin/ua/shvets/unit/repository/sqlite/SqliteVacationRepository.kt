package ua.shvets.unit.repository.sqlite

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.update
import ua.shvets.unit.db.VacationDAO
import ua.shvets.unit.db.VacationTable
import ua.shvets.unit.db.daoToModel
import ua.shvets.unit.db.suspendTransaction
import ua.shvets.unit.domain.Vacation
import ua.shvets.unit.repository.VacationRepository

class SqliteVacationRepository : VacationRepository {
    override suspend fun all(): List<Vacation> = suspendTransaction {
        VacationDAO.all().map(::daoToModel)
    }

    override suspend fun findById(id: Int): Vacation = suspendTransaction {
        VacationDAO
            .find { (VacationTable.id eq id) }
            .map(::daoToModel)
            .single()
    }

//    override suspend fun findByName(name: String): List<Vacation> = suspendTransaction {
//        VacationDAO
//            .find { (VacationTable.name eq name) }
//            .map(::daoToModel)
//    }

    override suspend fun add(vacation: Vacation): Unit = suspendTransaction {
        VacationDAO.new {
            start = vacation.start
            end = vacation.end
            approved = vacation.approved
//            employee = vacation.employee
        }
    }

    override suspend fun remove(id: Int): Boolean = suspendTransaction {
        val rowsDeleted = VacationTable.deleteWhere {
            VacationTable.id eq id
        }
        rowsDeleted == 1
    }

    override suspend fun update(vacation: Vacation): Unit = suspendTransaction {
        VacationTable.update({ VacationTable.id eq vacation.id }) {
            it[start] = vacation.start
            it[end] = vacation.end
            it[approved] = vacation.approved
            it[employee] = vacation.employee.id
            it[lastUpdateTime] = CurrentDateTime
        }
    }
}