package app.dfeverx.learningpartner.ui.screens.play

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.dfeverx.learningpartner.models.local.Option
import app.dfeverx.learningpartner.repos.PlayRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val playRepository: PlayRepository
) : ViewModel() {

    private val TAG = "PlayViewModel"
    private val _uiState = MutableStateFlow(PlayUiState())
    val uiState: StateFlow<PlayUiState> = _uiState.asStateFlow()
    private val levelId: Long = checkNotNull(savedStateHandle["levelId"])

    init {
        viewModelScope.launch {
            playRepository.questionsByLevel(levelId)?.let {
                _uiState.value = PlayUiState(
                    questions = it,
                    totalQuestionSize = it.size,
                    currentQuestionIndex = 0
                )
            }

        }
    }


    fun handleNextQuestion() {
        Log.d(TAG, "handleNext:  " + Gson().toJson(_uiState.value).toString())
        _uiState.value.attempt = listOf()
        if (_uiState.value.totalQuestionSize - 1 > _uiState.value.currentQuestionIndex) {
            _uiState.update { currentState ->
                currentState.copy(currentQuestionIndex = currentState.currentQuestionIndex + 1)
            }
        }
    }

    fun handleAttempt(selectedOption: Option) {
        Log.d(TAG, "handleAttempt: $selectedOption")
        val attemptInAttempt =
            _uiState.value.attempt.find { attempt -> attempt.content == selectedOption.content }
        _uiState.update { currentState ->
            if (attemptInAttempt == null) {
                currentState.copy(attempt = listOf(selectedOption))

            } else {
                currentState.copy(attempt = listOf(selectedOption))

            }
        }

    }


}