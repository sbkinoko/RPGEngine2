package core.domain

data class TextBoxData(
    val text: String,
    val callBack: () -> Unit = {},
)
