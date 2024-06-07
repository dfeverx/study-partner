package app.dfeverx.learningpartner.db.dao

import androidx.room.Dao
import androidx.room.Query
import app.dfeverx.learningpartner.models.local.Question

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions WHERE level = :levelId")
    suspend  fun questionsByLevel(levelId: Long):List<Question>?
}