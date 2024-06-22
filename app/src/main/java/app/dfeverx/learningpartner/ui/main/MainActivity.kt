package app.dfeverx.learningpartner.ui.main

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import app.dfeverx.learningpartner.ui.Screens
import app.dfeverx.learningpartner.ui.appNavHost
import app.dfeverx.learningpartner.ui.theme.StudyPartnerTheme
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.initialize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var auth: FirebaseAuth


    private val inAppUpdateListener: InstallStateUpdatedListener =
        InstallStateUpdatedListener {
            viewModel.inAppUpdateListener(it)
        }


    companion object {
        var appUpdateManager: AppUpdateManager? = null
        suspend fun Activity.startUpdate(appUpdateInfo: AppUpdateInfo): Int? {
            return appUpdateManager?.startUpdateFlow(
                appUpdateInfo, this, AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE)
//                        .setAllowAssetPackDeletion(true)
                    .build()
            )?.await()
        }

        suspend fun installUpdate(): Void? {
            return appUpdateManager?.completeUpdate()?.await()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth = Firebase.auth
        setContent {
            val navHostController = rememberNavController()

            StudyPartnerTheme {
                Surface(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                    appNavHost(
                        navController = navHostController,
                        startDestination = (if (auth.currentUser == null) Screens.Onboarding.route else Screens.Home.route)
                    )
                }
            }
        }
        Firebase.initialize(context = this)
        Firebase.appCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance(),
        )
        //        In-app update
        checkInAppUpdate()
    }

    private fun checkInAppUpdate() {

        appUpdateManager = AppUpdateManagerFactory.create(this)
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager?.appUpdateInfo

        appUpdateInfoTask?.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                appUpdateManager?.registerListener(inAppUpdateListener)
                viewModel.hasInAppUpdate(appUpdateInfo)
            } else {
                appUpdateManager?.unregisterListener(inAppUpdateListener)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            ?.appUpdateInfo
            ?.addOnSuccessListener {
                viewModel.hasAPendingUpdate(it)
            }
    }


}


