package app.dfeverx.learningpartner.ui.screens.levels

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.dfeverx.learningpartner.ui.Screens
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Levels(navController: NavController) {

    val items = List(100) { "Item ${it + 1}" }
    val levelInfoList = listOf(
        LevelInfo(
            name = "Level 1",
            tasks = listOf(
                Task(name = "Question", type = TaskType.MCQ, isCompleted = true),
                Task(name = "Reading", type = TaskType.MCQ, isCompleted = true),
                Task(name = "Writing", type = TaskType.MCQ, isCompleted = true),
                Task(name = "Listening", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Speaking", type = TaskType.MCQ, isCompleted = false),
            ),
            isCompleted = false
        ),
        LevelInfo(
            name = "Level 2",
            tasks = listOf(
                Task(name = "Question", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Reading", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Writing", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Listening", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Speaking", type = TaskType.MCQ, isCompleted = false),
            ),
            isCompleted = false
        ),
        LevelInfo(
            name = "Level 2",
            tasks = listOf(
                Task(name = "Question", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Reading", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Writing", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Listening", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Speaking", type = TaskType.MCQ, isCompleted = false),
            ),
            isCompleted = false
        ),
        LevelInfo(
            name = "Level 2",
            tasks = listOf(
                Task(name = "Question", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Reading", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Writing", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Listening", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Speaking", type = TaskType.MCQ, isCompleted = false),
            ),
            isCompleted = false
        ),
        LevelInfo(
            name = "Level 2",
            tasks = listOf(
                Task(name = "Question", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Reading", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Writing", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Listening", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Speaking", type = TaskType.MCQ, isCompleted = false),
            ),
            isCompleted = false
        ),
        LevelInfo(
            name = "Level 2",
            tasks = listOf(
                Task(name = "Question", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Reading", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Writing", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Listening", type = TaskType.MCQ, isCompleted = false),
                Task(name = "Speaking", type = TaskType.MCQ, isCompleted = false),
            ),
            isCompleted = false
        ),
    )
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
                            navController.popBackStack()
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
            stickyHeader {
//lazyGridState.

                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 8.dp),

                    text = levelInfoList[lazyGridState.firstVisibleItemIndex].name,
                    style = MaterialTheme.typography.titleLarge
                )

            }
            itemsIndexed(levelInfoList) { index, item ->
                LevelGroup(navController, item, index)
            }
        }
    })
}

@Composable
fun LevelGroup(navController: NavController, levelInfo: LevelInfo, index: Int) {
    if (index != 0) {
        Text(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp),
            text = "Level 1",
            style = MaterialTheme.typography.titleLarge
        )
    }
    levelInfo.tasks.forEach {
        LevelItem(
            modifier = Modifier
                .then(
                    if (it.isCompleted) Modifier.alpha(1f) else Modifier.alpha(
                        0.5f
                    )
                )
                .clickable {
                    navController.navigate(Screens.Play.route + "/" + "00")
                }
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            task = it
        )

    }
}


@Composable
fun LevelItem(modifier: Modifier, task: Task) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        AsyncImage(
            model = "https://picsum.photos/200/300",
            contentDescription = "Profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(78.dp)
                .clip(CircleShape)
                .border(
                    width = 4.dp,
                    color = if (task.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                )
                .padding(2.dp)
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = task.name,
            style = MaterialTheme.typography.bodyLarge
        )

    }

}

class LevelInfo(
    val name: String,
    val tasks: List<Task>,
    val isCompleted: Boolean
)

class Task(val name: String, val type: TaskType, val isCompleted: Boolean)

enum class TaskType {
    MCQ,
    LISTENING,
    WRITING,
    SPEAKING
}


@Preview
@Composable
fun Preview() {
    Levels(navController = NavController(LocalContext.current))
}