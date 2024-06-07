package app.dfeverx.learningpartner.ui.screens.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.dfeverx.learningpartner.R
import app.dfeverx.learningpartner.ui.components.ProgressCard
import app.dfeverx.learningpartner.utils.HexagonShape
import app.dfeverx.learningpartner.utils.drawCustomHexagonPath
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Analytics(navController: NavController) {
    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())


    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
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

                },
                scrollBehavior = scrollBehavior
            )

        }, content = {
            LazyColumn(modifier = Modifier.padding(it)) {
                item { PlayResult() }
            }


        })
}


@Composable
fun PlayResult() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(horizontal = 16.dp),
            text = "Congratulations",
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(horizontal = 16.dp),
            text = "Level 2 Unlocked",
            style = MaterialTheme.typography.titleLarge
        )


        val myShape = HexagonShape()
        Box(modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 32.dp)
            .drawWithContent {
                drawContent()
                drawPath(
                    path = drawCustomHexagonPath(size),
                    color = Color.Blue,
                    style = Stroke(
                        width = 10.dp.toPx(),
                        pathEffect = PathEffect.cornerPathEffect(40f)
                    )
                )
            }
            .wrapContentSize()
        ) {
            AsyncImage(
                model = R.drawable.ic_launcher_foreground,
                contentDescription = "My Hexagon Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .wrapContentSize()
                    .graphicsLayer {
                        shadowElevation = 8.dp.toPx()
                        shape = myShape
                        clip = true
                    }
                    .background(color = MaterialTheme.colorScheme.surface)
            )
        }

        ProgressCard {

        }

        Button(modifier = Modifier.padding(16.dp), onClick = { /*TODO*/ }) {
            Text(text = "Continue")
        }
    }
}
