package com.romanuel.core.domain

import com.romanuel.core.domain.model.AuthInfo

interface SessionStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}
