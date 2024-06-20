package ua.shvets.unit.repository

import ua.shvets.unit.domain.PersonalInformation

interface PersonalInformationRepository {
    suspend fun all(): List<PersonalInformation>
    suspend fun findById(id: Int): PersonalInformation
    suspend fun add(personalInformation: PersonalInformation)
    suspend fun remove(id: Int): Boolean
    suspend fun update(personalInformation: PersonalInformation)
}