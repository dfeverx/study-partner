package app.dfeverx.learningpartner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.dfeverx.learningpartner.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun MemoryFlashCount(isPro: Boolean = false, creditRemain: Long = 0, onClick: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.flash))
    val logoAnimationState = animateLottieCompositionAsState(
        composition = composition, iterations = 100, restartOnPlay = false
    )

    Row(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.onSurface,
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        LottieAnimation(modifier = Modifier
            .size(24.dp)
            .padding(0.dp),
            composition = composition,
            progress = { logoAnimationState.progress })
        Text(
            text = creditRemain.toString(),
            modifier = Modifier.padding(end = 4.dp),
            color = MaterialTheme.colorScheme.surface
        )
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = "Streaks",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}