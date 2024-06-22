package app.dfeverx.learningpartner.models.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "flashCards",
    foreignKeys = [ForeignKey(
        entity = StudyNote::class,
        parentColumns = ["id"],
        childColumns = ["studyNoteId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class FlashCard(
    @PrimaryKey(autoGenerate = false)
    val id: Long=-1,
    val front: String = "",
    val back: String = "",
    val studyNoteId: String = ""
) {
    var flagged = false
    var lastAttemptedIn: Long = 0

    var repetitions: Int = 0

    //    if the attempt is correct it increase by one other wise decrease by one
    var score: Int = 0
}