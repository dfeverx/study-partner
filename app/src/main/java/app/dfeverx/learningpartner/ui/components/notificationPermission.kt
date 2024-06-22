package app.dfeverx.learningpartner.ui.components

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationPermissionUI() {

    val notificationPermission = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Button(onClick = {
                if (!notificationPermission.status.isGranted) {
                    notificationPermission.launchPermissionRequest()
                } else {
                    Toast.makeText(context, "Permission Given Already", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Ask for permission")
            }
            Text(
                text = "Permission Status : ${notificationPermission.status.isGranted}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}