package app.dfeverx.learningpartner.ui

sealed class Screens(val route: String) {
    object Onboarding : Screens("onboarding_screen")
    object Home : Screens("home_screen")
    object NoteDetails : Screens("note_details_screen")
    object Levels : Screens("levels_screen")
    object Play : Screens("play_screen")
    object Statistics : Screens("statistics_screen")

}