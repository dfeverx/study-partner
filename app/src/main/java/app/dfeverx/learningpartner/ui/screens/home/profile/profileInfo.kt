package app.dfeverx.learningpartner.ui.screens.home.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import app.dfeverx.learningpartner.R

import coil.compose.AsyncImage
//import com.google.firebase.auth.FirebaseUser

@Composable
fun ProfileInfo(
//    userProfile: FirebaseUser?,
    isUserProfile: Boolean = true, isPremiumPlanVisible: Boolean = true,
    /*  creditRemain: State<CreditAndEndIn>,*/ modifier: Modifier, navHostController: NavController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(bottom = 16.dp)
    ) {
        val img =
            if (isUserProfile) /*user?.photoUrl*/ R.drawable.ic_launcher_foreground else /*msg.authorImage*/ R.drawable.ic_launcher_foreground
        AsyncImage(
            model = img, contentDescription = "Avatar",
            modifier = Modifier
                .padding(end = 8.dp)
                .clip(RoundedCornerShape(100.dp))
                .size(48.dp)
        )
        val proName = if (isUserProfile) "You" else "Chat awesome AI"
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = /*if (userProfile?.displayName == null) "---" else userProfile.displayName!!*/proName,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = if (isUserProfile) "---" else "hi@dfeverx.com",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        /* if (isPremiumPlanVisible) {
             CreditRemain(isPro = isPro.value, creditRemain = creditRemain.value.creditRemains) {
                 navHostController.navigate(Screen.SubscriptionScreen.route)
             }
         }*/

    }
}