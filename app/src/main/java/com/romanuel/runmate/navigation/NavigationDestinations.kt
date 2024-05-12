package com.romanuel.runmate.navigation

import com.romanuel.runmate.navigation.FeatureGraphs.AUTH_ROUTE
import com.romanuel.runmate.navigation.FeatureGraphs.RUN_ROUTE
import com.romanuel.runmate.navigation.Screens.INTRO_SCREEN
import com.romanuel.runmate.navigation.Screens.LOGIN_SCREEN
import com.romanuel.runmate.navigation.Screens.REGISTER_SCREEN
import com.romanuel.runmate.navigation.Screens.RUN_OVERVIEW_SCREEN

private object FeatureGraphs {
    const val AUTH_ROUTE = "auth"
    const val RUN_ROUTE = "run"
}

object FeatureDestinations {
    const val AUTH = AUTH_ROUTE
    const val RUN = RUN_ROUTE
}

private object Screens {
    const val INTRO_SCREEN = "intro"
    const val LOGIN_SCREEN = "login"
    const val REGISTER_SCREEN = "register"
    const val RUN_OVERVIEW_SCREEN = "run_overview"
}

object DestinationsScreens {

    const val INTRO = INTRO_SCREEN
    const val LOGIN = LOGIN_SCREEN
    const val REGISTER = REGISTER_SCREEN
    const val RUN_OVERVIEW = RUN_OVERVIEW_SCREEN
}
