package app.dfeverx.learningpartner.ui.screens.play

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.SpeakerPhone
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.dfeverx.learningpartner.models.local.Option
import app.dfeverx.learningpartner.models.local.Question
import app.dfeverx.learningpartner.ui.Screens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Play(navController: NavController) {
    val playViewModel = hiltViewModel<PlayViewModel>()
    val playUiState by playViewModel.uiState.collectAsState()

    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var showResultBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            /*confirmValueChange = {
                       Log.d("TAG", "Play: call i want to quit")
                       false
                   }*/
        )
    val progressAnimation by animateFloatAsState(
        targetValue = playUiState.progress(),
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing), label = "",
    )
    BackHandler(
        // your condition to enable handler
        enabled = true
    ) {
        // your action to be called if back handler is enabled
//        showWarningExitBottomSheet = true
        Log.d("TAG", "Play: i want to quit ")
    }
    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopAppBar(
            modifier = Modifier/*.borderBottom()*/,
            navigationIcon = {
                Icon(
                    Icons.Outlined.Close,
                    "Go back to previous",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .clickable {
                            navController.popBackStack()
                        }
                        .padding(8.dp),
                )
            },

            title = {

            },
            actions = {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(28.dp), trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    progress = progressAnimation
                )
            },
            scrollBehavior = scrollBehavior
        )

    }, content = {
        LazyColumn {
            item {
                MCQPlay(
                    modifier = Modifier.padding(it),
                    question = playUiState.currentQuestion(),
                    attempts = playUiState.attempt,
                    handleAttempt = playViewModel::handleAttempt
                )
            }
        }


    }, bottomBar = {
        FilledTonalButton(modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
            onClick = {

                showResultBottomSheet = true
            }) {
            Text(text = "Check ")
        }
    })
    if (showResultBottomSheet) {
        ModalBottomSheet(
            /* properties = ModalBottomSheetProperties(
                 shouldDismissOnBackPress = false,
                 isFocusable = true,
                 securePolicy = SecureFlagPolicy.SecureOn
             )*/
            onDismissRequest = {
                showResultBottomSheet = false
                playViewModel.handleNextQuestion()
                if (playUiState.totalQuestionSize - 1 == playUiState.currentQuestionIndex) {
                    navController.popBackStack()
                    navController.navigate(Screens.Analytics.route)
                }
            },
            sheetState = bottomSheetState
        ) {

            AttemptResultDialogueContent(
                modifier = Modifier, isCorrectAttempt = playUiState.validateAttempt(),
                continueToNext = {
                    showResultBottomSheet = false
                    playViewModel.handleNextQuestion()
                    if (playUiState.totalQuestionSize - 1 == playUiState.currentQuestionIndex) {
                        navController.popBackStack()
                        navController.navigate(Screens.Analytics.route)
                    }
                })

        }
    }
}


@Composable
fun MCQPlay(
    modifier: Modifier,
    question: Question,
    attempts: List<Option>,
    handleAttempt: (Option) -> Unit
) {
    Column(modifier = modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
        Row {
            Button(modifier = Modifier.height(32.dp), onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Outlined.SpeakerPhone,
                    "Read loud",
                    modifier = Modifier
//                        .padding(start = 8.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .clickable {
                        },
                )
                Text(modifier = Modifier.padding(start = 8.dp), text = "Read loud")
            }

        }
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = question.statement,
            style = MaterialTheme.typography.displayMedium
        )
        question.options.forEach {
            MCQOption(it, attempts, handleAttempt)

        }


    }
}


@Composable
fun MCQOption(option: Option, attempts: List<Option>, handleAttempt: (Option) -> Unit) {
    val result = attempts.find { atmpt -> atmpt.content == option.content }
    OutlinedButton(modifier = Modifier
        .padding(vertical = 16.dp, horizontal = 0.dp)
        .background(if (result == null) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant)
        .fillMaxWidth(),
//        colors = ButtonColors(containerColor = MaterialTheme.colorScheme.onSurface),
        shape = MaterialTheme.shapes.small,
        onClick = { handleAttempt(option) }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                modifier = Modifier.padding(0.dp),
                selected = result != null,
                onClick = { handleAttempt(option) })
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                text = option.content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Composable
fun AttemptResultDialogueContent(
    modifier: Modifier = Modifier,
    isCorrectAttempt: Boolean = false,
    continueToNext: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    if (isCorrectAttempt) Icons.Outlined.ThumbUp else Icons.Outlined.ThumbDown,
                    "Read loud",
                    modifier = Modifier
                        .size(36.dp)
//                        .padding(start = 8.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .clickable {
                        },
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = if (isCorrectAttempt) "Right" else "Wrong",
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }
        item {
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = "Explanation",
                    style = MaterialTheme.typography.bodySmall
                )
                HorizontalDivider()
                Text(
                    text = "Newtown laws of motion details the laws in physicsNewtown laws ...",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        item {
            Button(modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
                onClick = { continueToNext() }) {
                Text(text = "Go it ")
            }
        }
    }
}

@Preview
@Composable
fun PlayPreview() {
    Play(NavController(LocalContext.current))
}


@Preview
@Composable
fun PlayCorrectAnsPreview() {
    AttemptResultDialogueContent() {}
}


