package com.romanuel.auth.data

import com.romanuel.auth.data.model.RegisterRequestDTO
import com.romanuel.auth.domain.AuthRepository
import com.romanuel.core.data.networking.post
import com.romanuel.core.domain.util.DataError
import com.romanuel.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

private const val REGISTER_URL = "/register"
class AuthRepositoryImpl(
    private val httpClient: HttpClient,
) : AuthRepository {

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> =
         httpClient.post<RegisterRequestDTO, Unit>(
            route = REGISTER_URL,
            body = RegisterRequestDTO(
                email = email,
                password = password
            )
        )
}
