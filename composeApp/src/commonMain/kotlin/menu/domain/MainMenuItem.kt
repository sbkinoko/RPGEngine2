package menu.domain

data class MainMenuItem(
    val text: String,
    val onClick: () -> Unit,
)
