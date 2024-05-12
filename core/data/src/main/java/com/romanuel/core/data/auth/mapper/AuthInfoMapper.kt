package com.romanuel.core.data.auth.mapper

import com.romanuel.core.data.auth.model.AuthInfoSerializable
import com.romanuel.core.domain.model.AuthInfo

fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable = AuthInfoSerializable(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = userId
)

fun AuthInfoSerializable.toAuthInfo() = AuthInfo(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = userId
)
