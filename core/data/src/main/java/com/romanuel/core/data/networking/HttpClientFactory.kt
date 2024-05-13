package com.romanuel.core.data.networking

import com.romanuel.core.data.BuildConfig
import com.romanuel.core.data.networking.model.AccessTokenRequest
import com.romanuel.core.data.networking.model.AccessTokenResponse
import com.romanuel.core.data.networking.util.post
import com.romanuel.core.domain.auth.AuthInfo
import com.romanuel.core.domain.auth.SessionStorage
import com.romanuel.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

private const val BASE_KEY_HEADER = "x-api-key"
private const val EMPTY_STRING = ""
private const val ACCESS_TOKEN_URL = "/accessToken"

class HttpClientFactory(
    private val sessionStorage: SessionStorage,
) {
    fun build(): HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                header(BASE_KEY_HEADER, BuildConfig.API_KEY)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val info = sessionStorage.get()
                        BearerTokens(
                            accessToken = info?.accessToken ?: EMPTY_STRING,
                            refreshToken = info?.refreshToken ?: EMPTY_STRING
                        )
                    }
                    refreshTokens {
                        val info = sessionStorage.get()
                        val response = client.post<AccessTokenRequest, AccessTokenResponse>(
                            route = ACCESS_TOKEN_URL,
                            body = AccessTokenRequest(
                                refreshToken = info?.refreshToken ?: EMPTY_STRING,
                                userId = info?.userId ?: EMPTY_STRING
                            )
                        )
                        if (response is Result.Success) {
                            val newAuthInfo = AuthInfo(
                                accessToken = response.data.accessToken,
                                refreshToken = info?.refreshToken ?: EMPTY_STRING,
                                userId = info?.userId ?: EMPTY_STRING
                            )
                            sessionStorage.set(newAuthInfo)
                            BearerTokens(
                                accessToken = newAuthInfo.accessToken,
                                refreshToken = newAuthInfo.refreshToken
                            )
                        } else {
                            BearerTokens(
                                accessToken = EMPTY_STRING,
                                refreshToken = EMPTY_STRING
                            )
                        }
                    }
                }
            }
        }
}
