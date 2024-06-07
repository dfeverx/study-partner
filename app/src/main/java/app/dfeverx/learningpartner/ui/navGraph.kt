package app.dfeverx.learningpartner.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.dfeverx.learningpartner.ui.screens.analytics.Analytics
import app.dfeverx.learningpartner.ui.screens.home.Home
import app.dfeverx.learningpartner.ui.screens.levels.Levels
import app.dfeverx.learningpartner.ui.screens.notes.NoteDetails
import app.dfeverx.learningpartner.ui.screens.play.Play


@Composable
fun appNavHost(
    startDestination: String = Screens.Home.route,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.Home.route) {
            Home(navController)
        }
        composable(Screens.NoteDetails.route) {

        }
        composable(
            route = Screens.NoteDetails.route + "/{noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.LongType
                    defaultValue = 0 // Your parameter goes here
                },
            ),
        ) {
            NoteDetails(navController)
        }
        composable(
            route = Screens.Levels.route + "/{noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                    defaultValue = "" // Your parameter goes here
                },
            ),
        ) {
            Levels(navController)
        }
        composable(
            route = Screens.Play.route + "/{levelId}",
            arguments = listOf(
                navArgument("levelId") {
                    type = NavType.LongType
                    defaultValue = 0 // Your parameter goes here
                },
            ),
        ) {
            Play(
                navController
            )
        }
        composable(route = Screens.Analytics.route) {
            Analytics(navController = navController)
        }

    }

}