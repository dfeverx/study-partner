package app.dfeverx.learningpartner.models.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "levels",
    foreignKeys = [ForeignKey(
        entity = StudyNote::class,
        parentColumns = ["id"],
        childColumns = ["studyNoteId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Level(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val stage: Int,//serially numbered from 1
    val studyNoteId: String,
    val questionIds: List<String>,
    val isCompleted: Boolean,
    val isRevision: Boolean = false
) {
    var accuracy: Int = 0
}

