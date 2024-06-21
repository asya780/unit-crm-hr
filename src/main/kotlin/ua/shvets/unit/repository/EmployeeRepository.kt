package ua.shvets.unit.repository

import ua.shvets.unit.domain.Employee

interface EmployeeRepository {
    suspend fun all(): List<Employee>
    suspend fun findById(id: Int): Employee
    suspend fun findByName(name: String): List<Employee>
    suspend fun add(employee: Employee)
    suspend fun remove(id: Int): Boolean
    suspend fun update(employee: Employee)
}