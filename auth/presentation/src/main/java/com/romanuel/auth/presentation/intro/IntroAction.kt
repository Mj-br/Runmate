package com.romanuel.auth.presentation.intro

sealed interface IntroAction {

    data object OnSignInClick : IntroAction
    data object OnSignUpClick : IntroAction
}
