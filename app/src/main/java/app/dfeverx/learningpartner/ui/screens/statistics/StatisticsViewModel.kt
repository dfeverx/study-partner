package app.dfeverx.learningpartner.ui.screens.statistics

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.dfeverx.learningpartner.LearningPartnerApplication
import app.dfeverx.learningpartner.receivers.OnAlarmTriggeredReceiver
import app.dfeverx.learningpartner.repos.LevelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val levelRepository: LevelRepository,
    private val alarmManager: AlarmManager, private val application: LearningPartnerApplication
) : ViewModel() {
    private val TAG = "StatisticsViewModel"
    private val MINIMUM_THRESHOLD_ACCURACY_FOR_NEXT_LEVEL = 60
    private val noteId: String = checkNotNull(savedStateHandle["noteId"])
    private val currentLevelId: Long = checkNotNull(savedStateHandle["levelId"])
    private val score: Int = checkNotNull(savedStateHandle["score"])
    private val attemptCount: Int =
        checkNotNull(savedStateHandle["attemptCount"])//totalQuestionAttemptedCount
    private val totalNumberOfQuestions: Int =
        checkNotNull(savedStateHandle["totalNumberOfQuestions"])
    private val stage: Int =
        checkNotNull(savedStateHandle["stage"])
    private val accuracy = ((score.toFloat() / totalNumberOfQuestions) * 100).toInt()


    private val _uiState =
        MutableStateFlow(
            StatisticsUiState(
                stage = stage,
                score = score,
                accuracy = accuracy,
                isSuccessfulAttempt = accuracy >= MINIMUM_THRESHOLD_ACCURACY_FOR_NEXT_LEVEL,
            )
        )
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            giveStreak()
//            minimum accuracy threshold for creating new level is 60% accuracy other wise show failed retry
            if (accuracy >= MINIMUM_THRESHOLD_ACCURACY_FOR_NEXT_LEVEL) {
                val levelCreationResult =
                    levelRepository.createNewLevelOnlyIfNotExist(noteId, stage, accuracy)
//                todo: show the level creation message if failed option to retry
//               clear all notification and create  schedule notification
                scheduleNotificationForNextAttempt()
                Log.d(TAG, "Init: level creation $levelCreationResult")

            }
        }
    }

    private fun scheduleNotificationForNextAttempt() {
        val intent = Intent(application, OnAlarmTriggeredReceiver::class.java).apply {
            this.putExtra("noteId", noteId)
        }
        val pendingIntent =
            PendingIntent.getBroadcast(application, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val futureTime = System.currentTimeMillis() + 1000 * 10
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    futureTime,
                    pendingIntent
                )
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                futureTime,
                pendingIntent
            )
        }
    }

    private fun giveStreak() {
//        todo: update local , firestore
    }

    private fun isEligibleForStreak() {
//        todo Not yet implemented
    }

    fun playRetry(): String {
        return "/${noteId}/${currentLevelId}/${stage}"
    }


}

