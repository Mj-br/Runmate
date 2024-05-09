package com.romanuel.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDTO(
    val email: String,
    val password: String,
)
