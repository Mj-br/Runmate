package com.romanuel.core.data.auth

import android.content.SharedPreferences
import com.romanuel.core.data.auth.mapper.toAuthInfo
import com.romanuel.core.data.auth.mapper.toAuthInfoSerializable
import com.romanuel.core.data.auth.model.AuthInfoSerializable
import com.romanuel.core.domain.SessionStorage
import com.romanuel.core.domain.model.AuthInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EncryptedSessionStorage(
    private val sharedPreferences: SharedPreferences,
) : SessionStorage {
    override suspend fun get(): AuthInfo? =
        withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_AUTH_INFO, null)
            json?.let { Json.decodeFromString<AuthInfoSerializable>(it).toAuthInfo() }
        }

    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {
            if (info == null) {
                sharedPreferences.edit().remove(KEY_AUTH_INFO).apply()
                return@withContext
            }

            val json = Json.encodeToString(info.toAuthInfoSerializable())
            sharedPreferences
                .edit()
                .putString(KEY_AUTH_INFO, json)
                .commit()
        }
    }

    companion object {
        const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
}
