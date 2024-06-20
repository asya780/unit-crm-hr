package ua.shvets.unit.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    val id: Int,
    val name: String,
    val surname: String,
    val middleName: String,
    val birthDate: LocalDate,
    val multiplier: Float,
    val active: Boolean,
    val dismissalDate: LocalDate?,
    val creationTime: LocalDateTime,
    val lastUpdateTime: LocalDateTime,
    val position: Position,
    val personalInformation: PersonalInformation,
    val department: Department,
    val cabinet: Cabinet
)
