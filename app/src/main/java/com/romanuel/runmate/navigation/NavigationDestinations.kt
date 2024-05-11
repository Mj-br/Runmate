package com.romanuel.runmate.navigation

import com.romanuel.runmate.navigation.Graphs.AUTH_ROUTE
import com.romanuel.runmate.navigation.Screens.HOME_SCREEN
import com.romanuel.runmate.navigation.Screens.INTRO_SCREEN
import com.romanuel.runmate.navigation.Screens.LOGIN_SCREEN
import com.romanuel.runmate.navigation.Screens.REGISTER_SCREEN
import com.romanuel.runmate.navigation.Screens.RUN_SCREEN

private object Graphs {
    const val AUTH_ROUTE = "auth"
}
private object Screens {
    const val INTRO_SCREEN = "intro"
    const val HOME_SCREEN = "home"
    const val LOGIN_SCREEN = "login"
    const val REGISTER_SCREEN = "register"
    const val RUN_SCREEN = "run"
}

object DestinationsScreens {

    const val INTRO = INTRO_SCREEN
    const val HOME = HOME_SCREEN
    const val LOGIN = LOGIN_SCREEN
    const val REGISTER = REGISTER_SCREEN
    const val RUN = RUN_SCREEN
}

object GraphsDestinations {
    const val AUTH = AUTH_ROUTE
}
