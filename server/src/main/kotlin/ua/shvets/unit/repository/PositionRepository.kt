package ua.shvets.unit.repository

import ua.shvets.unit.domain.Position

interface PositionRepository {
    suspend fun all(): List<Position>
    suspend fun findById(id: Int): Position
    suspend fun findByName(name: String): List<Position>
    suspend fun add(position: Position)
    suspend fun remove(id: Int): Boolean
    suspend fun update(position: Position)
}