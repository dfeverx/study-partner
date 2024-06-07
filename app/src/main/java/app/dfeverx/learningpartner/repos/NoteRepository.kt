package app.dfeverx.learningpartner.repos

import app.dfeverx.learningpartner.db.AppDatabase
import app.dfeverx.learningpartner.models.local.StudyNote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(private val appDatabase: AppDatabase) {
    fun studyNoteById(studyNoteId: Long): Flow<StudyNote> {
        return appDatabase.studyNotesDao().studyNoteById(studyNoteId)
    }
}