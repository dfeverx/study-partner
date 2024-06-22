package app.dfeverx.learningpartner.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import app.dfeverx.learningpartner.LearningPartnerApplication
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.model.InstallStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: LearningPartnerApplication) : ViewModel() {
    private val TAG = "ActivityViewModel $application"
    private val _inAppUpdateState: MutableStateFlow<InAppUpdateUiState> =
        MutableStateFlow(InAppUpdateUiState())
    val inAppUpdateState: StateFlow<InAppUpdateUiState>
        get() = _inAppUpdateState

    fun inAppUpdateListener(state: InstallState) {
        Log.d(TAG, "inAppUpdateListener: $state")
        when (state.installStatus()) {
            InstallStatus.DOWNLOADING -> {
                val bytesDownloaded = state.bytesDownloaded()
                val totalBytesToDownload = state.totalBytesToDownload()
                val progress = bytesDownloaded.toFloat() / totalBytesToDownload
                _inAppUpdateState.update { currentState ->
                    currentState.progress = progress
                    currentState.isReadyToInstall = false
                    currentState.isDownloading = true
                    return
                }
            }

            InstallStatus.DOWNLOADED -> {
                _inAppUpdateState.update { currentState ->
                    currentState.progress = 0f
                    currentState.isReadyToInstall = true
                    currentState.isDownloading = false
                    return
                }
            }

            else -> {}
        }
    }

    fun hasAPendingUpdate(appUpdateInfo: AppUpdateInfo) {
        Log.d(TAG, "inAppUpdateSuccessListener: $appUpdateInfo")
        if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
            _inAppUpdateState.update { currentState ->
                currentState.progress = 1f
                currentState.isReadyToInstall = true
                return
            }
        }
    }

    fun hasInAppUpdate(appUpdateInfo: AppUpdateInfo) {
        Log.d(TAG, "hasInAppUpdate: $appUpdateInfo")
        _inAppUpdateState.update { currentState ->
            currentState.appUpdateInfo = appUpdateInfo
            currentState.progress = 0f
            currentState.isReadyToInstall = false
            currentState.isDownloading = false
            return
        }
    }
}

