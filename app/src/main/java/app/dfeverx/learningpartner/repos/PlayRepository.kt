package app.dfeverx.learningpartner.repos

import app.dfeverx.learningpartner.db.AppDatabase
import app.dfeverx.learningpartner.models.local.Option
import app.dfeverx.learningpartner.models.local.Question
import javax.inject.Inject

class PlayRepository @Inject constructor(private val appDatabase: AppDatabase) {
    suspend fun questionsByLevel(levelId: Long): List<Question>? {
//        return appDatabase.questionDao().questionsByLevel(levelId)
        return listOf(
            Question(
                0,
                0,
                "Who is the prime minister of india?",
                listOf(
                    Option("Narendra modi", true),
                    Option("Nethaji", false),
                    Option("Nehru", false),
                    Option("Other", false),
                ),
                difficulty = 0,
                explanation = "",
                level = 1,
                time = 0
            ),
            Question(
                0,
                0,
                "Who is the first prime minister of india",
                listOf(
                    Option("Narendra modi", false),
                    Option("Nethaji", false),
                    Option("Other", false),
                    Option("Nehru", true),
                ),
                difficulty = 0,
                explanation = "",
                level = 1,
                time = 0
            ),
            Question(
                0,
                0,
                "Who is the best prime minister of india",
                listOf(
                    Option("Narendra modi", false),
                    Option("Nehru", false),
                    Option("Nethaji", false),
                    Option("Other", true),
                ),
                difficulty = 0,
                explanation = "",
                level = 1,
                time = 0
            ),
        )
    }
}