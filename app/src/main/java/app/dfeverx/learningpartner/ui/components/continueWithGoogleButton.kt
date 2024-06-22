package app.dfeverx.learningpartner.ui.components

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import app.dfeverx.learningpartner.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun ContinueWithGoogleButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit,
    updateLoading: (Boolean) -> Unit
) {
    val auth = Firebase.auth


    val context = LocalContext.current

    val gso: GoogleSignInOptions = GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(context, R.string.web_client_id))
        .requestEmail().build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("TAG", "ContinueWithGoogleButton: ${result.resultCode},${Activity.RESULT_OK}")
//            if (result.resultCode == Activity.RESULT_OK) {
            Log.d("TAG", "ContinueWithGoogleButton: result ok ${result.data}")
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data!!)
            // Handle successful sign in with task

            Log.d("TAG", "ContinueWithGoogleButton: isSuccess")
            try {
                // Google SignIn was successful, authenticate with Firebase
                val account: GoogleSignInAccount =
                    task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth
                    .signInWithCredential(credential)
                    .addOnSuccessListener {
                        Log.d("TAG", "ContinueWithGoogleButton: $it")
                        onSuccess()
                    }.addOnFailureListener {
                        Log.d("TAG", "ContinueWithGoogleButton: $it")
                        onFailure(it)
                    }

            } catch (e: Exception) {
                Log.d("TAG", "ContinueWithGoogleButton: ${e.message}")
                onFailure(e)

            }
            Log.d("TAG", "ContinueWithGoogleButton: end")
        }




    Button(
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface),
        onClick = {
            updateLoading(true)
            launcher.launch(googleSignInClient.signInIntent)
        }) {
        Image(
            painter = painterResource(id = R.drawable.google_logo),
            contentDescription = "",
            Modifier
                .padding(end = 8.dp)
                .size(30.dp)
        )
        Text(
            modifier = Modifier.padding(6.dp),
            text = "Continue with Google",
            color = MaterialTheme.colorScheme.surface
        )
    }
}



