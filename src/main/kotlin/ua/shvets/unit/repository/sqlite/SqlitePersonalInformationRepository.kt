package ua.shvets.unit.repository.sqlite

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.update
import ua.shvets.unit.db.PersonalInformationDAO
import ua.shvets.unit.db.PersonalInformationTable
import ua.shvets.unit.db.daoToModel
import ua.shvets.unit.db.suspendTransaction
import ua.shvets.unit.domain.PersonalInformation
import ua.shvets.unit.repository.PersonalInformationRepository

class SqlitePersonalInformationRepository : PersonalInformationRepository {
    override suspend fun all(): List<PersonalInformation> = suspendTransaction {
        PersonalInformationDAO.all().map(::daoToModel)
    }

    override suspend fun findById(id: Int): PersonalInformation = suspendTransaction {
        PersonalInformationDAO
            .find { (PersonalInformationTable.id eq id) }
            .map(::daoToModel)
            .single()
    }

    override suspend fun add(personalInformation: PersonalInformation): Unit = suspendTransaction {
        PersonalInformationDAO.new {
            registrationAddress = personalInformation.registrationAddress
            address = personalInformation.address
            personalNumber = personalInformation.personalNumber
        }
    }

    override suspend fun remove(id: Int): Boolean = suspendTransaction {
        val rowsDeleted = PersonalInformationTable.deleteWhere {
            PersonalInformationTable.id eq id
        }
        rowsDeleted == 1
    }

    override suspend fun update(personalInformation: PersonalInformation): Unit = suspendTransaction {
        PersonalInformationTable.update({ PersonalInformationTable.id eq personalInformation.id }) {
            it[registrationAddress] = personalInformation.registrationAddress
            it[address] = personalInformation.address
            it[lastUpdateTime] = CurrentDateTime
        }
    }
}