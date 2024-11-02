package core.domain

data class Choice(
    val text: String,
    val callBack: () -> Unit,
)
