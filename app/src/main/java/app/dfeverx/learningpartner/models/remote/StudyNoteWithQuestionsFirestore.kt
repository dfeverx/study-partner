package app.dfeverx.learningpartner.models.remote

import app.dfeverx.learningpartner.models.StudyNoteStatus
import app.dfeverx.learningpartner.models.local.FlashCard
import app.dfeverx.learningpartner.models.local.Option
import app.dfeverx.learningpartner.models.local.Question
import app.dfeverx.learningpartner.models.local.StudyNote


data class StudyNoteWithQuestionsFirestore(
    var id: String = "",
    var docUrl: String = "",
    var title: String = "",
    var summary: String = "",
    var markdown: String = "",
    var questions: List<QuestionFirestore> = listOf(),
    var flashCards: List<FlashCard> = listOf(),
    var keyPoints: List<String> = listOf(),
    var thumbnail: String = "",
    var subject: String = "",
    var srcLng: List<String> = listOf(),
    var status: StudyNoteStatus = StudyNoteStatus.DOC_CREATED
) {
    fun toStudyNoteLocal(): StudyNote {
        return StudyNote(
            id = this.id,
            docUrl = this.docUrl,
            title = this.title,
            summary = this.summary,
            markdown = this.markdown,
            keyPoints = this.keyPoints,
            thumbnail = this.thumbnail,
            srcLng = this.srcLng,
            subject = this.subject,
            totalLevel = this.questions.size / 5,//todo: move to remoteConfig
            status = this.status
        )
    }

    fun toQuestionsLocal(): List<Question> {
        return this.questions.map { fq ->
            Question(
                id = fq.id,
                statement = fq.sm,
                studyNoteId = this.id,
                options = fq.op.map { op -> Option(content = op.text, isCorrect = op.isCorrect) },
                difficulty = 0,
                explanation = fq.ex
            )
        }
    }
}