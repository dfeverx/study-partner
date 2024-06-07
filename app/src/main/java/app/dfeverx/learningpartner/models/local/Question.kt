package app.dfeverx.learningpartner.models.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import app.dfeverx.learningpartner.db.converter.OptionTypeConverter

@Entity(
    tableName = "questions",
    foreignKeys = [ForeignKey(
        entity = StudyNote::class,
        parentColumns = ["id"],
        childColumns = ["studyNoteId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class Question(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val studyNoteId: Long,
    val statement: String,
    @TypeConverters(OptionTypeConverter::class)
    val options: List<Option>,
    val difficulty: Int,//max 10 min 1
    val explanation: String,
    val time: Long,
    val level:Int=0
) {
    var flagged = false
    var correctAttempts = 0
}

