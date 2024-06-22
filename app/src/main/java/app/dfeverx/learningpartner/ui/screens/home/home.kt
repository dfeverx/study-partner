package app.dfeverx.learningpartner.ui.screens.home

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import app.dfeverx.learningpartner.R
import app.dfeverx.learningpartner.models.local.StudyNote
import app.dfeverx.learningpartner.services.DocumentUploadService
import app.dfeverx.learningpartner.ui.Screens
import app.dfeverx.learningpartner.ui.components.MemoryFlashCount
import app.dfeverx.learningpartner.ui.main.MainViewModel
import app.dfeverx.learningpartner.ui.screens.Scanner
import app.dfeverx.learningpartner.ui.screens.home.update.InAppUpdateCard
import app.dfeverx.learningpartner.utils.activityViewModel
import app.dfeverx.learningpartner.utils.borderBottom
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController) {

    val mainViewModel = activityViewModel<MainViewModel>()
    val inAppUpdateState = mainViewModel.inAppUpdateState.collectAsState()
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val noteCategories by homeViewModel.categories.collectAsState()
    val studyNotes = homeViewModel.studyNotes.collectAsState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showProfileBs by remember { mutableStateOf(false) }
    val activity = LocalContext.current as Activity
    val firebaseUser = Firebase.auth


    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), bottomBar = {

    }, topBar = {
        TopAppBar(modifier = Modifier, title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    modifier = Modifier.size(52.dp),
                    model = R.drawable.ic_launcher_foreground,
                    contentDescription = "Ninaiva logo icon",

                    )
                AsyncImage(
                    modifier = Modifier.height(16.dp),
                    model = R.drawable.ninaiva_typography_logo,
                    contentDescription = "Ninaiva logo typography",

                    )

            }
        }, scrollBehavior = scrollBehavior, actions = {
            AnimatedVisibility(!showProfileBs) {
                MemoryFlashCount(
                    true, creditRemain = 0
                ) {}
            }


            IconButton(onClick = {

            }, modifier = Modifier.padding(end = 8.dp)) {

                AsyncImage(model = if (firebaseUser.currentUser?.isAnonymous == true) "https://api.dicebear.com/8.x/avataaars-neutral/png?seed=Precious" else firebaseUser.currentUser?.photoUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .size(32.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            showProfileBs = true
                        })

            }
        })
    }, floatingActionButton = {
        if (studyNotes.value.isNotEmpty()) {
            Scanner(modifier = Modifier.padding(end = 8.dp),
                expanded = lazyStaggeredGridState.firstVisibleItemIndex < 2,
                rawPdfUri = { uri, text ->
                    homeViewModel.addNote("Ready" + uri.path)
                    val serviceIntent = Intent(activity, DocumentUploadService::class.java)
                    serviceIntent.data = uri
                    activity.startService(serviceIntent)

                })
        }
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            if (studyNotes.value.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .then(
                            if (lazyStaggeredGridState.firstVisibleItemScrollOffset > 0) Modifier.borderBottom() else Modifier.borderBottom(
                                color = Color.Transparent
                            )
                        )
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(noteCategories) {
                        AssistChip(
                            modifier = Modifier.padding(end = 16.dp),
                            onClick = { homeViewModel.handleCategorySelection(it.value) },
                            label = { Text(it.label) },
                            leadingIcon = {
                                Icon(
                                    it.icon,
                                    contentDescription = "Localized description",
                                    Modifier.size(AssistChipDefaults.IconSize)

                                )
                            },
                            shape = MaterialTheme.shapes.extraLarge
                        )
                    }
                }
            }
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                state = lazyStaggeredGridState,
//                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(
                    top = 0.dp, bottom = 32.dp, start = 16.dp, end = 16.dp
                ),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (studyNotes.value.isNotEmpty()) {
                    item(span = StaggeredGridItemSpan.FullLine) {

                    }
                }
//                show inapp update only if appupdate info available
                if (inAppUpdateState.value.appUpdateInfo != null) {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        InAppUpdateCard(state = inAppUpdateState.value)
                    }
                }

                items(studyNotes.value) { studyNote ->
                    StudyNoteOverviewItem(studyNote = studyNote,
                        homeViewModel = homeViewModel,
                        onClickNote = {
                            navController.navigate(
                                Screens.NoteDetails.route + "/" + it.id
                            )
                        },
                        onClickTag = {})
                }

            }
        }

        /*
          */
        if (studyNotes.value.isEmpty()) {
            EmptyStudyNote(action = {
                Scanner(modifier = Modifier.padding(horizontal = 8.dp),
                    expanded = true,
                    rawPdfUri = { uri, text ->
                        homeViewModel.addNote(text)
                        val serviceIntent = Intent(activity, DocumentUploadService::class.java)
                        serviceIntent.data = uri
                        activity.startService(serviceIntent)
                    })
            })
        }
    })
    if (showProfileBs) {
        ModalBottomSheet(
            onDismissRequest = {
                showProfileBs = false
            },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom)
        ) {

            UserProfileBottomSheetContent(userProfile = firebaseUser.currentUser,
                navController,
                true,
                newConversation = { },
                actionDone = {
                    showProfileBs = false
                })

        }
    }
}


@Composable
fun StudyNoteOverviewItem(
    studyNote: StudyNote,
    homeViewModel: HomeViewModel,
    onClickNote: (StudyNote) -> Unit,
    onClickTag: (String) -> Unit,
) {
    OutlinedCard(modifier = Modifier/*.padding(8.dp)*/, onClick = { onClickNote(studyNote) }) {
        AsyncImage(
            modifier = Modifier.height(220.dp),
            model = studyNote.thumbnail,
            contentDescription = "thumbnail image for " + studyNote.title,
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            AssistChip(modifier = Modifier,
                onClick = { /*TODO*/ },
                label = { Text(text = studyNote.subject) })


        }
        Text(
            text = studyNote.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = studyNote.summary, modifier = Modifier.padding(8.dp)
        )
        Text(
            text = homeViewModel.formatTime(studyNote.nextLevelIn),
            modifier = Modifier.padding(8.dp)
        )

    }
}


@Composable
fun EmptyStudyNote(action: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(172.dp),
            model = R.drawable.illu_team_brainstorming,
            contentDescription = "Translated description of what the image contains",

            )
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = "Add your first note and start your new learning experience",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
        action()


    }

}


class TextIcon(val icon: ImageVector, val label: String, val value: String)