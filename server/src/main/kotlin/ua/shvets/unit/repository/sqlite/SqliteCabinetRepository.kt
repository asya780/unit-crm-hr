package ua.shvets.unit.repository.sqlite

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.update
import ua.shvets.unit.db.CabinetDAO
import ua.shvets.unit.db.CabinetTable
import ua.shvets.unit.db.daoToModel
import ua.shvets.unit.db.suspendTransaction
import ua.shvets.unit.domain.Cabinet
import ua.shvets.unit.repository.CabinetRepository

class SqliteCabinetRepository : CabinetRepository {
    override suspend fun all(): List<Cabinet> = suspendTransaction {
        CabinetDAO.all().map(::daoToModel)
    }

    override suspend fun findById(id: Int): Cabinet = suspendTransaction {
        CabinetDAO
            .find { (CabinetTable.id eq id) }
            .map(::daoToModel)
            .single()
    }

    override suspend fun findByName(name: String): List<Cabinet> = suspendTransaction {
        CabinetDAO
            .find { (CabinetTable.name eq name) }
            .map(::daoToModel)
    }

    override suspend fun add(cabinet: Cabinet): Unit = suspendTransaction {
        CabinetDAO.new {
            name = cabinet.name
            floor = cabinet.floor
        }
    }

    override suspend fun remove(id: Int): Boolean = suspendTransaction {
        val rowsDeleted = CabinetTable.deleteWhere {
            CabinetTable.id eq id
        }
        rowsDeleted == 1
    }

    override suspend fun update(cabinet: Cabinet): Unit = suspendTransaction {
        CabinetTable.update({ CabinetTable.id eq cabinet.id }) {
            it[name] = cabinet.name
            it[floor] = cabinet.floor
            it[lastUpdateTime] = CurrentDateTime
        }
    }
}