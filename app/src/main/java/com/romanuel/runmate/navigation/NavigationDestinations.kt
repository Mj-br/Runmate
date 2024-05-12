package com.romanuel.runmate.navigation

import com.romanuel.runmate.navigation.Features.AUTH_GRAPH_ROUTE
import com.romanuel.runmate.navigation.Features.RUN_GRAPH_ROUTE
import com.romanuel.runmate.navigation.Screens.ACTIVE_RUN_SCREEN
import com.romanuel.runmate.navigation.Screens.INTRO_SCREEN
import com.romanuel.runmate.navigation.Screens.LOGIN_SCREEN
import com.romanuel.runmate.navigation.Screens.REGISTER_SCREEN
import com.romanuel.runmate.navigation.Screens.RUN_OVERVIEW_SCREEN

private object Features {
    const val AUTH_GRAPH_ROUTE = "auth"
    const val RUN_GRAPH_ROUTE = "run"
}

object FeatureDestinations {
    const val AUTH = AUTH_GRAPH_ROUTE
    const val RUN = RUN_GRAPH_ROUTE
}

private object Screens {
    const val INTRO_SCREEN = "intro"
    const val LOGIN_SCREEN = "login"
    const val REGISTER_SCREEN = "register"
    const val RUN_OVERVIEW_SCREEN = "run_overview"
    const val ACTIVE_RUN_SCREEN = "active_run"
}

object DestinationsScreens {

    const val INTRO = INTRO_SCREEN
    const val LOGIN = LOGIN_SCREEN
    const val REGISTER = REGISTER_SCREEN
    const val RUN_OVERVIEW = RUN_OVERVIEW_SCREEN
    const val ACTIVE_RUN = ACTIVE_RUN_SCREEN
}
