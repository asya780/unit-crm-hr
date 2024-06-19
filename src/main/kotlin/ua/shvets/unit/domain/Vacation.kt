package ua.shvets.unit.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class Vacation(
    val id: Int,
    val start: LocalDate,
    val end: LocalDate,
    val approved: Boolean,
    val creationTime: LocalDateTime,
    val lastUpdateTime: LocalDateTime,
)