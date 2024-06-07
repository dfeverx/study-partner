package app.dfeverx.learningpartner.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.dfeverx.learningpartner.models.local.StudyNote
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyNoteDao {
    @Query("SELECT * FROM study_notes ORDER BY id DESC")
    fun studyNotes(): Flow<List<StudyNote>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(studyNote: StudyNote): Long

    @Query("SELECT * FROM study_notes WHERE id = :studyNoteId")
    fun studyNoteById(studyNoteId: Long): Flow<StudyNote>
}