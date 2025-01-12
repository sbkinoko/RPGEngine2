package gamescreen.text

data class TextBoxData(
    val text: String,
    val callBack: (() -> Unit)? = null,
)
