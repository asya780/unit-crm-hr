package ua.shvets.unit.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class Department(
    val id: Int,
    val name: String,
    val creationTime: LocalDateTime,
    val lastUpdateTime: LocalDateTime
)