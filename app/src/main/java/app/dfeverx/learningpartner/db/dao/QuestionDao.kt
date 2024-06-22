package app.dfeverx.learningpartner.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.dfeverx.learningpartner.models.local.Question
import app.dfeverx.learningpartner.models.local.StudyNote

@Dao
interface QuestionDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuestions(questions: List<Question>): List<Long>

    @Query("SELECT * FROM questions WHERE id IN (:questionIds)")
    suspend fun questionsByIds(questionIds: List<String>): List<Question>

    @Query("UPDATE questions SET repetitions = repetitions + 1, score = :score WHERE id=:questionId")
    suspend fun updateScoreAndRepetitionInQuestion(questionId: String, score: Int): Int

}