package com.romanuel.auth.presentation.register

import com.romanuel.core.presentation.ui.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess : RegisterEvent
    data class Error(val error: UiText) : RegisterEvent
}
