package com.romanuel.runmate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.romanuel.auth.presentation.intro.IntroScreenRoot
import com.romanuel.auth.presentation.login.LoginScreenRoot
import com.romanuel.auth.presentation.register.RegisterScreenRoot
import com.romanuel.runmate.navigation.DestinationsScreens.INTRO
import com.romanuel.runmate.navigation.DestinationsScreens.LOGIN
import com.romanuel.runmate.navigation.DestinationsScreens.REGISTER
import com.romanuel.runmate.navigation.DestinationsScreens.RUN
import com.romanuel.runmate.navigation.GraphsDestinations.AUTH

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = AUTH
    ) {
        authGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = INTRO,
        route = AUTH
    ) {
        composable(INTRO) {
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigate(REGISTER)
                },
                onSignInClick = {
                    navController.navigate(LOGIN)
                }
            )
        }
        composable(REGISTER) {
            RegisterScreenRoot(
                onSignInClick = {
                    navController.navigate(LOGIN) {
                        popUpTo(REGISTER) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    navController.navigate(LOGIN)
                }
            )
        }
        composable(LOGIN) {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate(RUN) {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate(REGISTER) {
                        popUpTo("login") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
    }
}
