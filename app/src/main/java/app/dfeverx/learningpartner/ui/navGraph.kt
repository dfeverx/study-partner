package app.dfeverx.learningpartner.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import app.dfeverx.learningpartner.ui.screens.statistics.Statistics
import app.dfeverx.learningpartner.ui.screens.home.Home
import app.dfeverx.learningpartner.ui.screens.levels.Levels
import app.dfeverx.learningpartner.ui.screens.notes.NoteDetails
import app.dfeverx.learningpartner.ui.screens.onboarding.Onboarding
import app.dfeverx.learningpartner.ui.screens.play.Play


@Composable
fun appNavHost(
    startDestination: String = Screens.Home.route,
    navController: NavHostController,
) {
    val uri = "ninaiva://app"
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screens.Onboarding.route) {
            Onboarding(navController)
        }
        composable(Screens.Home.route) {
            Home(navController)
        }

        composable(
            route = Screens.NoteDetails.route + "/{noteId}",
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/noteId={noteId}" }),
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                    defaultValue = ""// Your parameter goes here
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
            route = Screens.Play.route + "/{noteId}/{levelId}/{stage}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                    defaultValue = "" // Your parameter goes here
                },
                navArgument("levelId") {
                    type = NavType.LongType
                    defaultValue = 0 // Your parameter goes here
                },
                navArgument("stage") {
                    type = NavType.IntType
                    defaultValue = 0 // Your parameter goes here
                },
            ),
        ) {
            Play(
                navController
            )
        }
        composable(route = Screens.Statistics.route + "/{noteId}/{levelId}/{score}/{attemptCount}/{totalNumberOfQuestions}/{stage}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("levelId") {
                    type = NavType.LongType
                    defaultValue = 0
                },
                navArgument("score") {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("attemptCount") {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("totalNumberOfQuestions") {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("stage") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            Statistics(navController = navController)
        }

    }

}