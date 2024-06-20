package ua.shvets.unit.repository

import ua.shvets.unit.domain.Department

interface DepartmentRepository {
    suspend fun all(): List<Department>
    suspend fun findById(id: Int): Department
    suspend fun findByName(name: String): List<Department>
    suspend fun add(department: Department)
    suspend fun remove(id: Int): Boolean
    suspend fun update(department: Department)
}