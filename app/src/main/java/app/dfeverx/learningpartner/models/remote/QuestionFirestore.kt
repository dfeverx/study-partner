package app.dfeverx.learningpartner.models.remote


class QuestionFirestore(
    val id: String = "",
    val sm: String = "",
    val op: List<OptionFirestore> = listOf(),
    val ex: String = "",
)
