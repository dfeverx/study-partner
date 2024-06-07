package app.dfeverx.learningpartner.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import app.dfeverx.learningpartner.db.converter.MarkdownStringListConverter

@Entity(tableName = "study_notes")
class StudyNote(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    var isProcessing: Boolean = true,
) {
    var title: String = "Science and technology"
    var summary: String = "Content translated description of what the image contains"
    var fullContent: String = ""

    @TypeConverters(MarkdownStringListConverter::class)
    var keyPoints = listOf<String>()

    @TypeConverters(MarkdownStringListConverter::class)
    var tags: List<String> = listOf()
    var subject: String = ""
    var category: String = ""
    var subCategory: String = ""
    var thumbnail: String =
        "https://images.pexels.com/photos/18929339/pexels-photo-18929339/free-photo-of-pink-flowers-in-a-glass-bottle-on-the-corner-of-the-table.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    var docRemoteUrl: String = ""

    @TypeConverters(MarkdownStringListConverter::class)//Todo: add string instead of markdownArray
    var sourceLang = listOf<String>()
    var docLocalUrl = ""
    var isInLongTermMemory = false

    var createdAt: Long = 0
    var updatedAt: Long = 0
    var currentLevel: Long = 0
}