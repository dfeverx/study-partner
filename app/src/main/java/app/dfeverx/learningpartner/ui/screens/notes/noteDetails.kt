package app.dfeverx.learningpartner.ui.screens.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import app.dfeverx.learningpartner.models.local.StudyNote
import app.dfeverx.learningpartner.ui.Screens
import app.dfeverx.learningpartner.ui.components.ProgressCard
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetails(navController: NavHostController) {
    val noteViewModel = hiltViewModel<NoteViewModel>()
    val noteDetails by noteViewModel.studyNote.collectAsState()
    val noteId = navController.currentBackStackEntry?.arguments?.getString("noteId")
    val lazyGridState = rememberLazyListState()
    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())


    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopAppBar(
            navigationIcon = {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowBack,
                    "Go back to previous",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .clickable {
                            navController.navigateUp()
                        }
                        .padding(8.dp),
                )
            },
            title = {

            },
            scrollBehavior = scrollBehavior
        )

    }, content = {

        LazyColumn(
            state = lazyGridState,
            modifier = Modifier
                .padding(it)

        ) {

            item {
                ProgressCard(
                    modifier = Modifier,
                    levelStage = noteDetails.currentStage - 1,
                    score = noteDetails.score,
                    accuracy = 30,
                    levelProgress = ((noteDetails.currentStage - 1)) / noteDetails.totalLevel.toFloat(),
                    isPlayButtonVisible = true,
                    continuePlay = { navController.navigate(Screens.Levels.route + "/" + noteId) })
            }
            item {
                NoteHeaderDetails(noteDetails)
            }

        }
    })
}

@Composable
fun NoteHeaderDetails(noteDetails: StudyNote) {
    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 8.dp),
            text = noteDetails.title,
            style = MaterialTheme.typography.titleLarge
        )
        AsyncImage(
            modifier = Modifier
                .height(220.dp)
                .padding(horizontal = 0.dp, vertical = 8.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            model = noteDetails.thumbnail,
            contentDescription = "thumbnail image for ",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 4.dp),
            text = "Summary:",
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = noteDetails.summary,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 4.dp),
            text = "Key points:",
            style = MaterialTheme.typography.labelSmall
        )

        noteDetails.keyPoints.forEach {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = it,
                style = MaterialTheme.typography.bodyLarge
            )
        }


    }
}



