package com.romanuel.auth.domain

import com.romanuel.core.domain.util.DataError
import com.romanuel.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}
