package ua.shvets.unit.repository

import ua.shvets.unit.domain.Vacation

interface VacationRepository {
    suspend fun all(): List<Vacation>
    suspend fun findById(id: Int): Vacation
    suspend fun add(vacation: Vacation)
    suspend fun remove(id: Int): Boolean
    suspend fun update(vacation: Vacation)
}