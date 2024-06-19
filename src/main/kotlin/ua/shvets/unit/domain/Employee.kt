package ua.shvets.unit.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.serializers.LocalDateComponentSerializer
import kotlinx.datetime.serializers.LocalDateTimeComponentSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    val id: Int,
    val name: String,
    val surname: String,
    val middleName: String,
    @Serializable(with = LocalDateComponentSerializer::class)
    val birthDate: LocalDate,
    val multiplier: Float,
    val active: Boolean,
    @Serializable(with = LocalDateComponentSerializer::class)
    val dismissalDate: LocalDate,
    @Serializable(with = LocalDateTimeComponentSerializer::class)
    val creationTime: LocalDateTime,
    @Serializable(with = LocalDateTimeComponentSerializer::class)
    val lastUpdateTime: LocalDateTime,
)
