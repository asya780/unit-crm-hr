package ua.shvets.unit.repository.sqlite

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.update
import ua.shvets.unit.db.CabinetTable.floor
import ua.shvets.unit.db.DepartmentDAO
import ua.shvets.unit.db.DepartmentTable
import ua.shvets.unit.db.daoToModel
import ua.shvets.unit.db.suspendTransaction
import ua.shvets.unit.domain.Department
import ua.shvets.unit.repository.DepartmentRepository

class SqliteDepartmentRepository : DepartmentRepository {
    override suspend fun all(): List<Department> = suspendTransaction {
        DepartmentDAO.all().map(::daoToModel)
    }

    override suspend fun findById(id: Int): Department = suspendTransaction {
        DepartmentDAO
            .find { (DepartmentTable.id eq id) }
            .map(::daoToModel)
            .single()
    }

    override suspend fun findByName(name: String): List<Department> = suspendTransaction {
        DepartmentDAO
            .find { (DepartmentTable.name eq name) }
            .map(::daoToModel)
    }

    override suspend fun add(department: Department): Unit = suspendTransaction {
        DepartmentDAO.new {
            name = department.name
            creationTime = department.creationTime
            lastUpdateTime = department.lastUpdateTime
        }
    }

    override suspend fun remove(id: Int): Boolean = suspendTransaction {
        val rowsDeleted = DepartmentTable.deleteWhere {
            DepartmentTable.id eq id
        }
        rowsDeleted == 1
    }

    override suspend fun update(department: Department): Unit = suspendTransaction {
        DepartmentTable.update({ DepartmentTable.id eq department.id }) {
            it[name] = department.name
            it[lastUpdateTime] = CurrentDateTime
        }
    }
}