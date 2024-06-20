package ua.shvets.unit.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class PersonalInformation(
    val id: Int,
    val registrationAddress: String,
    val address: String,
    val personalNumber: String,
    val creationTime: LocalDateTime,
    val lastUpdateTime: LocalDateTime,
)