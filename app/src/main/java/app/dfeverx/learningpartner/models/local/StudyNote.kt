package app.dfeverx.learningpartner.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import app.dfeverx.learningpartner.db.converter.MarkdownStringListConverter
import app.dfeverx.learningpartner.models.StudyNoteStatus

@Entity(tableName = "study_notes")
class StudyNote(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val docUrl: String,
    var title: String = "",
    var summary: String = "",
    @TypeConverters(MarkdownStringListConverter::class)
    var keyPoints: List<String> = listOf(),
    var subject: String = "",
    var thumbnail: String = "",
    var markdown: String = "",
    @TypeConverters(MarkdownStringListConverter::class)//Todo: add string instead of markdownArray
    var srcLng: List<String> = listOf(),
    var totalLevel: Int = 5,
    var status: StudyNoteStatus = StudyNoteStatus.DOC_CREATED
) {
    var isProcessing: Boolean = true

    var docLocalUrl = ""

    //    analytics

    var currentStage: Int = 1

    //    next repetition date in unix timestamp seconds
    var nextLevelIn: Long = 0

    //    over all score
    var score: Int = 0 // max 5

    // if all the levels are completed and all the questions are answered correctly with out any mistakes at least three times
    var isInLTM = false

    //    todo

    var createdAt: Long = 0
    var updatedAt: Long = 0

}