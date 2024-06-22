package app.dfeverx.learningpartner.repos

import android.util.Log
import app.dfeverx.learningpartner.db.AppDatabase
import app.dfeverx.learningpartner.models.local.Question
import javax.inject.Inject

class PlayRepository @Inject constructor(private val appDatabase: AppDatabase) {
    private val TAG = "PlayRepository"
    suspend fun questionsByLevelId(levelId: Long): List<Question> {
//        read level by level id
        val level = appDatabase.levelDao().getLevelById(levelId)
        Log.d(TAG, "questionsByLevel: level $level")
//        read list of question by ids
        val questionIds = level.questionIds
        Log.d(TAG, "questionsByLevel: $questionIds")
        val questions = appDatabase.questionDao().questionsByIds(questionIds)
        Log.d(TAG, "questionsByLevel: $questions")
        return questions
    }


    suspend fun updateAttemptInQuestion(questionId: String, updatedScore: Int): Int {
        return appDatabase.questionDao().updateScoreAndRepetitionInQuestion(questionId, updatedScore)
    }
}