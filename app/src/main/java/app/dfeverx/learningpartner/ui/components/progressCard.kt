package app.dfeverx.learningpartner.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.LocationSearching
import androidx.compose.material.icons.outlined.PlayCircleFilled
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ProgressCard(continuePlay: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.Lightbulb,
                "Go back to previous",
                modifier = Modifier
                    .size(42.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .clickable {
                    }
                    .padding(8.dp),
            )
            Text(
                modifier = Modifier,
                text = "4",
                style = MaterialTheme.typography.titleLarge
            )
            VerticalDivider(
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .height(32.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            Icon(
                Icons.Outlined.LocationSearching,
                "Go back to previous",
                modifier = Modifier
                    .size(42.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .clickable {
                    }
                    .padding(8.dp)
                ,
            )
            Text(
                modifier = Modifier,
                text = "5",
                style = MaterialTheme.typography.titleLarge
            )


        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "Go faster",
                    style = MaterialTheme.typography.titleLarge
                )
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "12/20", style = MaterialTheme.typography.labelSmall)
                    LinearProgressIndicator(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        progress = .2f
                    )
                }
            }

            Icon(
                Icons.Outlined.PlayCircleFilled,
                "Go back to previous",
                modifier = Modifier
                    .size(64.dp)

                    .clip(MaterialTheme.shapes.extraLarge)
                    .clickable {
                        continuePlay()
                    }
                    .padding(8.dp),
            )
        }
    }
}