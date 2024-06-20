package ua.shvets.unit.repository.sqlite

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.update
import ua.shvets.unit.db.PositionDAO
import ua.shvets.unit.db.PositionTable
import ua.shvets.unit.db.daoToModel
import ua.shvets.unit.db.suspendTransaction
import ua.shvets.unit.domain.Position
import ua.shvets.unit.repository.PositionRepository

class SqlitePositionRepository : PositionRepository {
    override suspend fun all(): List<Position> = suspendTransaction {
        PositionDAO.all().map(::daoToModel)
    }

    override suspend fun findById(id: Int): Position = suspendTransaction {
        PositionDAO
            .find { (PositionTable.id eq id) }
            .map(::daoToModel)
            .single()
    }

    override suspend fun findByName(name: String): List<Position> = suspendTransaction {
        PositionDAO
            .find { (PositionTable.name eq name) }
            .map(::daoToModel)
    }

    override suspend fun add(position: Position): Unit = suspendTransaction {
        PositionDAO.new {
            name = position.name
            salary = position.salary
        }
    }

    override suspend fun remove(id: Int): Boolean = suspendTransaction {
        val rowsDeleted = PositionTable.deleteWhere {
            PositionTable.id eq id
        }
        rowsDeleted == 1
    }

    override suspend fun update(position: Position): Unit = suspendTransaction {
        PositionTable.update({ PositionTable.id eq position.id }) {
            it[name] = position.name
            it[salary] = position.salary
            it[lastUpdateTime] = CurrentDateTime
        }
    }
}