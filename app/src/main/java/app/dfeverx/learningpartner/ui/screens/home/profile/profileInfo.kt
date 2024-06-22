package app.dfeverx.learningpartner.ui.screens.home.profile

import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.dfeverx.learningpartner.ui.components.ContinueWithGoogleButton
import app.dfeverx.learningpartner.ui.components.MemoryFlashCount
import app.dfeverx.learningpartner.ui.components.NotificationPermissionUI

import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseUser


@Composable
fun ProfileInfo(
    userProfile: FirebaseUser?,
    isUserProfile: Boolean = true,
    isPremiumPlanVisible: Boolean = true,
    /*  creditRemain: State<CreditAndEndIn>,*/
    modifier: Modifier, navHostController: NavController
) {
    Column {


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            val img =
                if (userProfile?.isAnonymous == false) userProfile?.photoUrl else "https://api.dicebear.com/8.x/avataaars-neutral/png?seed=Precious"


            AsyncImage(
                model = img, contentDescription = "Avatar",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .size(48.dp)
            )

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = if (userProfile?.isAnonymous == false) userProfile.displayName!! else "Anonymous",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = if (userProfile?.isAnonymous == false) userProfile.email!! else "---",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

            }

            Spacer(modifier = Modifier.weight(1f))

            MemoryFlashCount(isPro = true, creditRemain = 0) {
            }


        }
        if (userProfile?.isAnonymous == true) {
            ContinueWithGoogleButton(
                modifier = Modifier.padding(horizontal = 32.dp),
                enabled = true, onSuccess = {

                }, onFailure = {
                    Log.d("TAG", "ProfileInfo:google auth error $it")
                }
            ) {
            }

            Text(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(16.dp),
                text = "To make sure you never lose your hard-earned achievements , please consider continue with Google . It’s that simple "
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            NotificationPermissionUI()
        }
    }
}