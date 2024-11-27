package gamescreen.choice

data class Choice(
    val text: String,
    val callBack: () -> Unit,
)
