package ua.shvets.unit.domain

import kotlinx.datetime.LocalDateTime


data class Cabinet(
    val id: Int,
    val name: String,
    val floor: Int,
    val creationTime: LocalDateTime,
    val lastUpdateTime: LocalDateTime
)