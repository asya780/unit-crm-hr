package ua.shvets.unit.repository.sqlite

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.update
import ua.shvets.unit.db.CabinetDAO
import ua.shvets.unit.db.DepartmentDAO
import ua.shvets.unit.db.EmployeeDAO
import ua.shvets.unit.db.EmployeeTable
import ua.shvets.unit.db.PersonalInformationDAO
import ua.shvets.unit.db.PositionDAO
import ua.shvets.unit.db.daoToModel
import ua.shvets.unit.db.suspendTransaction
import ua.shvets.unit.domain.Employee
import ua.shvets.unit.repository.EmployeeRepository

class SqliteEmployeeRepository : EmployeeRepository {
    override suspend fun all(): List<Employee> = suspendTransaction {
        EmployeeDAO.all().map(::daoToModel)
    }

    override suspend fun findById(id: Int): Employee = suspendTransaction {
        EmployeeDAO
            .find { (EmployeeTable.id eq id) }
            .map(::daoToModel)
            .single()
    }

    override suspend fun findByName(name: String): List<Employee> = suspendTransaction {
        EmployeeDAO
            .find { (EmployeeTable.name eq name) }
            .map(::daoToModel)
    }

    override suspend fun add(employee: Employee): Unit = suspendTransaction {
        EmployeeDAO.new {
            name = employee.name
            surname = employee.surname
            middleName = employee.middleName
            birthDate = employee.birthDate
            multiplier = employee.multiplier
            active = employee.active
            dismissalDate = employee.dismissalDate
            position = PositionDAO[employee.position.id]
            personalInformation = PersonalInformationDAO[employee.personalInformation.id]
            department = DepartmentDAO[employee.department.id]
            cabinet = CabinetDAO[employee.cabinet.id]
        }
    }

    override suspend fun remove(id: Int): Boolean = suspendTransaction {
        val rowsDeleted = EmployeeTable.deleteWhere {
            EmployeeTable.id eq id
        }
        rowsDeleted == 1
    }

    override suspend fun update(employee: Employee): Unit = suspendTransaction {
        EmployeeTable.update({ EmployeeTable.id eq employee.id }) {
            it[name] = employee.name
            it[name] = employee.name
            it[surname] = employee.surname
            it[middleName] = employee.middleName
            it[birthDate] = employee.birthDate
            it[multiplier] = employee.multiplier
            it[active] = employee.active
            it[dismissalDate] = employee.dismissalDate
            it[lastUpdateTime] = CurrentDateTime
        }
    }
}