package app.dfeverx.learningpartner.repos

import android.util.Log
import androidx.room.withTransaction
import app.dfeverx.learningpartner.db.AppDatabase
import app.dfeverx.learningpartner.models.local.StudyNoteWithLevels
import app.dfeverx.learningpartner.models.local.genLevel
import com.google.type.DateTime
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.math.pow
import kotlin.time.Duration.Companion.days

class LevelRepository @Inject constructor(private val appDatabase: AppDatabase) {
    private val TAG = "LevelRepository"

    //    minimum  40 required
    private fun calculateNextIntervalInDaysUsingSpaceRepetitionMinAccuracy40(
        currentLevel: Int,
        accuracy: Int,
        userPerformance: List<Int>
    ): Int {
        if (accuracy !in 40..100) {
            throw IllegalArgumentException("accuracy must be between 10 and 100")
        }

        // Calculate base interval with exponential growth
        val baseInterval = 2.0.pow((currentLevel - 1).toDouble()).toInt()

        // Determine accuracy factor
        val accuracyFactor = when (accuracy) {
            in 40..49 -> 0.5
            in 50..59 -> 0.75
            in 60..69 -> 1.0
            in 70..79 -> 1.25
            in 80..89 -> 1.5
            in 90..100 -> 2.0
            else -> throw IllegalArgumentException("Invalid accuracy")
        }

        // Calculate user performance factor
        val averagePerformance = userPerformance.average()
        val performanceFactor = when (averagePerformance) {
            in 0.0..59.9 -> 0.5
            in 60.0..69.9 -> 0.75
            in 70.0..79.9 -> 1.0
            in 80.0..89.9 -> 1.25
            in 90.0..100.0 -> 1.5
            else -> throw IllegalArgumentException("Invalid average performance")
        }

        // Introduce a decay factor for poor performance over time
        val decayFactor = if (userPerformance.any { it < 50 }) 0.75 else 1.0

        // Calculate final interval
        return (baseInterval * accuracyFactor * performanceFactor * decayFactor).toInt()
            .coerceAtLeast(1)
    }

    fun studyNoteWithQuestions(studyNoteId: String): Flow<StudyNoteWithLevels?> {
        return appDatabase.levelDao().studyNoteWithLevels(studyNoteId)
    }


    //    use this only the note details and question are available in the db,return nextAttempt data so in vm schedule notification
    suspend fun createNewLevelOnlyIfNotExist(
        studyNoteId: String, stage: Int, accuracy: Int
    ): Long {
        return appDatabase.withTransaction {
            val currentUnix = System.currentTimeMillis()
            val newStage = stage + 1
//            make sure that it is not double entry
            val level = appDatabase.levelDao().levelByNoteId(studyNoteId)
            val alreadyHaveLevel = level.find { lvl -> lvl.stage == newStage }
            Log.d(TAG, "createNewLevel: $alreadyHaveLevel")
            if (alreadyHaveLevel != null) {
                Log.d(TAG, "createNewLevelOnlyIfNotExist: Double entry founded")
                return@withTransaction -1
            }
//            find next interval
            val attemptHistory = level.map { lvl -> lvl.accuracy }
            val nextIntervalInDays =
                calculateNextIntervalInDaysUsingSpaceRepetitionMinAccuracy40(
                    stage,
                    accuracy,
                    attemptHistory
                )

            Log.d(TAG, "createNewLevelOnlyIfNotExist: Next interval in days $nextIntervalInDays")
            val nextAttemptUnix = currentUnix + nextIntervalInDays.days.inWholeMilliseconds
            Log.d(TAG, "createNewLevelOnlyIfNotExist: nextAttempt unix $nextAttemptUnix")
//add current level accuracy to level
            appDatabase.levelDao().updateAccuracyAndScore(studyNoteId, stage, accuracy)

//            read study note details and all question belongs to that study note
            val studyNoteWithAllQuestions =
                appDatabase.studyNotesDao().studyNoteWithQuestions(studyNoteId)


//            update to study notes stage
            appDatabase
                .studyNotesDao()
                .updateStudyNoteStage(
                    studyNoteId = studyNoteWithAllQuestions!!.studyNote!!.id,
                    stage = newStage,
                    nextLevelIn = nextAttemptUnix //unix timestamp
                )
//            create new level
            appDatabase.levelDao()
                .addLevel(
                    studyNoteWithAllQuestions.studyNote!!.genLevel(
                        allQuestions = studyNoteWithAllQuestions.questions!!,
                        stage = newStage
                    )
                )

        }
    }


}