package menu.main

data class MainMenuItem(
    val text: String,
    val onClick: () -> Unit,
)
