package app.dfeverx.learningpartner.repos

import android.util.Log
import app.dfeverx.learningpartner.db.AppDatabase
import app.dfeverx.learningpartner.models.local.Level
import app.dfeverx.learningpartner.models.local.Question
import app.dfeverx.learningpartner.models.local.StudyNote
import app.dfeverx.learningpartner.models.local.genLevel
import app.dfeverx.learningpartner.models.remote.StudyNoteWithQuestionsFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.math.ceil

class StudyNoteRepository @Inject constructor(private val appDatabase: AppDatabase) {
    private val TAG = "StudyNoteRepository"
    fun studyNoteById(studyNoteId: String): Flow<StudyNote> {
        return appDatabase.studyNotesDao().studyNoteById(studyNoteId)
    }

    suspend fun addStudyNoteAndQuestionsFromFirestore(content: StudyNoteWithQuestionsFirestore) {
//        todo:convert to db transaction
        try {
            val localStudyNote = content.toStudyNoteLocal()
            val localQuestions = content.toQuestionsLocal()
            val snDbRes = appDatabase.studyNotesDao().addStudyNote(localStudyNote)
            val qstnDbRes = appDatabase.questionDao().addQuestions(localQuestions)
//            create initial level
            val levelDbRes =
                appDatabase.levelDao().addLevel(localStudyNote.genLevel(localQuestions))
            Log.d(
                TAG,
                "addStudyNoteAndQuestionsFromFirestore: inserted sn $snDbRes, qs $qstnDbRes , level $levelDbRes"
            )
        } catch (e: Exception) {
            Log.d(TAG, "insertStudyNotesContent: $e")
        }
    }
}

