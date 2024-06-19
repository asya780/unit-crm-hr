package ua.shvets.unit.domain

import kotlinx.datetime.LocalDateTime

data class Position(
    val id: Int,
    val name: String,
    val salary: Int,
    val creationTime: LocalDateTime,
    val lastUpdateTime: LocalDateTime
)
