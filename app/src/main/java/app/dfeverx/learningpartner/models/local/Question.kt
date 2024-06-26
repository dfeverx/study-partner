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
    val id: String,
    val studyNoteId: String,
    val statement: String,
    @TypeConverters(OptionTypeConverter::class)
    val options: List<Option>,
    val difficulty: Int,// max 10 min 1
    val explanation: String,

    ) {
    var flagged = false
    var lastAttemptedIn: Long = 0

    var repetitions: Int = 0
//    if the attempt is correct it increase by one other wise decrease by one
    var score:Int = 0
}

