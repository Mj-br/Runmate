package com.romanuel.auth.data

import com.romanuel.auth.data.model.LoginRequest
import com.romanuel.auth.data.model.LoginResponse
import com.romanuel.auth.data.model.RegisterRequest
import com.romanuel.auth.domain.AuthRepository
import com.romanuel.core.data.networking.util.post
import com.romanuel.core.domain.SessionStorage
import com.romanuel.core.domain.auth.AuthInfo
import com.romanuel.core.domain.util.DataError
import com.romanuel.core.domain.util.EmptyResult
import com.romanuel.core.domain.util.Result
import com.romanuel.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient

private const val REGISTER_URL = "/register"
private const val LOGIN_URL = "/login"
class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage,
) : AuthRepository {
    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = LOGIN_URL,
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if (result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> =
         httpClient.post<RegisterRequest, Unit>(
            route = REGISTER_URL,
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
}
