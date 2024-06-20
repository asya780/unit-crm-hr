package ua.shvets.unit.repository

import ua.shvets.unit.domain.Cabinet

interface CabinetRepository {
    suspend fun all(): List<Cabinet>
    suspend fun findById(id: Int): Cabinet
    suspend fun findByName(name: String): List<Cabinet>
    suspend fun add(cabinet: Cabinet)
    suspend fun remove(id: Int): Boolean
    suspend fun update(cabinet: Cabinet)
}