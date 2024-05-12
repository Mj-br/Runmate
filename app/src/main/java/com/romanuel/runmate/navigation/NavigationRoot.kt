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
import com.romanuel.run.presentation.active_run.ActiveRunScreenRoot
import com.romanuel.run.presentation.run_overview.RunOverviewScreenRoot
import com.romanuel.runmate.navigation.DestinationsScreens.ACTIVE_RUN
import com.romanuel.runmate.navigation.DestinationsScreens.INTRO
import com.romanuel.runmate.navigation.DestinationsScreens.LOGIN
import com.romanuel.runmate.navigation.DestinationsScreens.REGISTER
import com.romanuel.runmate.navigation.DestinationsScreens.RUN_OVERVIEW
import com.romanuel.runmate.navigation.FeatureDestinations.AUTH
import com.romanuel.runmate.navigation.FeatureDestinations.RUN

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) RUN else AUTH
    ) {
        authGraph(navController)
        runGraph(navController)
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
                        popUpTo(AUTH) {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate(REGISTER) {
                        popUpTo(LOGIN) {
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

private fun NavGraphBuilder.runGraph(navController: NavHostController) {
    navigation(
        startDestination = RUN_OVERVIEW,
        route = RUN
    ) {
        composable(RUN_OVERVIEW) {
            RunOverviewScreenRoot(
                onStartRunClick = {
                    navController.navigate(ACTIVE_RUN)
                }
            )
        }
        composable(ACTIVE_RUN) {
            ActiveRunScreenRoot()
        }
    }
}
