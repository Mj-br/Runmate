package com.romanuel.core.data.networking.model

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenRequest(
    val refreshToken: String,
    val userId: String,
)
