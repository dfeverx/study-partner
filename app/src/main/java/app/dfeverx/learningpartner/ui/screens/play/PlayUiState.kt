package app.dfeverx.learningpartner.ui.screens.play

import app.dfeverx.learningpartner.models.local.Option
import app.dfeverx.learningpartner.models.local.Question

data class PlayUiState(
    val questions: List<Question>? = null,
    val totalQuestionSize: Int = 0,
    var currentQuestionIndex: Int = -1,
    var attempt: List<Option> = listOf(),
) {
    fun validateAttempt(): Boolean {
        val result = this.attempt.find { atmpt -> !atmpt.isCorrect }
        return result == null && this.attempt.isNotEmpty()
    }

    fun currentQuestion(): Question {
        return questions?.get(this.currentQuestionIndex)!!
    }

    fun progress(): Float {
        return this.currentQuestionIndex.toFloat() / (this.totalQuestionSize - 1)
    }


}