package app.dfeverx.learningpartner.repos

import app.dfeverx.learningpartner.db.AppDatabase
import app.dfeverx.learningpartner.models.local.StudyNote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor(private val appDatabase: AppDatabase) {
    suspend fun addNote(pdfUri: String): Long {
        val si = StudyNote(id = (0..1000).random().toLong())
        si.summary = pdfUri
        return appDatabase.studyNotesDao().addNote(si)

    }

    val studyNotes: Flow<List<StudyNote>> = appDatabase.studyNotesDao().studyNotes()
}