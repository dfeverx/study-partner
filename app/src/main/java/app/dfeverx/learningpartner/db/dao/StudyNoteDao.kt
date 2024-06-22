package app.dfeverx.learningpartner.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import app.dfeverx.learningpartner.models.local.StudyNote
import app.dfeverx.learningpartner.models.local.StudyNoteWithQuestions
import kotlinx.coroutines.flow.Flow


@Dao
interface StudyNoteDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStudyNote(studyNote: StudyNote): Long

    @Query("SELECT * FROM study_notes WHERE id = :studyNoteId")
    fun studyNoteById(studyNoteId: String): Flow<StudyNote>
    @Query("SELECT * FROM study_notes WHERE id = :studyNoteId")
    suspend fun studyNoteByIdOneShot(studyNoteId: String): StudyNote

    @Query("SELECT * FROM study_notes ORDER BY id DESC")
    fun studyNotes(): Flow<List<StudyNote>>


    @Transaction
    @Query("SELECT * FROM study_notes WHERE id = :noteId")
    suspend fun studyNoteWithQuestions(noteId: String): StudyNoteWithQuestions?

    @Query("UPDATE study_notes SET currentStage = :stage ,nextLevelIn = :nextLevelIn WHERE id=:studyNoteId")
    suspend fun updateStudyNoteStage(studyNoteId: String, stage: Int,nextLevelIn:Long):Int





}