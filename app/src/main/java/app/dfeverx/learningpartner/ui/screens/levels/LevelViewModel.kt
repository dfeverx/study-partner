package app.dfeverx.learningpartner.ui.screens.levels

import android.app.AlarmManager
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.dfeverx.learningpartner.LearningPartnerApplication
import app.dfeverx.learningpartner.models.LEVEL_EMOJI_NAME
import app.dfeverx.learningpartner.models.local.StudyNoteWithLevels
import app.dfeverx.learningpartner.repos.LevelRepository
import app.dfeverx.learningpartner.utils.hasPermission
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val levelRepository: LevelRepository,
    private val application: LearningPartnerApplication,
    private val alarmManager: AlarmManager
) : ViewModel() {
    private val TAG = "LevelViewModel"
    private val studyNoteId: String = checkNotNull(savedStateHandle["noteId"])

    //    todo: shimmer anim
    private val _studyNoteWithLevels: MutableStateFlow<List<LevelUI?>> = MutableStateFlow(
        listOf()
    )
    val levels: MutableStateFlow<List<LevelUI?>>
        get() = _studyNoteWithLevels


    init {
        viewModelScope.launch(Dispatchers.IO) {
            levelRepository.studyNoteWithQuestions(studyNoteId).collect { studyNote ->
                _studyNoteWithLevels.value = studyNote?.uiLevels() ?: listOf()
                Log.d(TAG, "init: $studyNoteId")
                Log.d(TAG, "init: ${studyNote?.levels}")
                Log.d(TAG, "init: ${studyNote?.studyNote}")
            }

        }
    }

    fun hasAlarmPermission(): Boolean {
        Log.d(TAG, "hasAlarmPermission: ${alarmManager.hasPermission()}")
        return alarmManager.hasPermission()
    }


}


private fun StudyNoteWithLevels.uiLevels(): List<LevelUI> {

    val listOfUiLevel = mutableListOf<LevelUI>()
    when (this.studyNote) {
        null -> {
            //todo:shimmer anim
        }

        else -> {
            for (i in 1..this.studyNote?.totalLevel!!)
                listOfUiLevel.add(
                    LevelUI(
                        levelId = this.levels?.getOrNull(i - 1)?.id,//todo: improve
                        stage = i,
                        currentStage = this.studyNote?.currentStage ?: -1,
                        icon = LEVEL_EMOJI_NAME[i - 1].first,
                        name = LEVEL_EMOJI_NAME[i - 1].second,
                        isPlayable = this.studyNote!!.currentStage >= i
                    )
                )
        }
    }

    return listOfUiLevel
}

data class LevelUI(
    val levelId: Long?,
    val stage: Int,
    val icon: String,
    val name: String,
    val isPlayable: Boolean = false,
    val currentStage: Int = -1
)