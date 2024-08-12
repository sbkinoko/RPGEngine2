package menu.domain

enum class MenuType(
    val title: String = "",
) {
    Main,
    Status(
        title = "ステータス",
    ),
    Item1(title = "text1"),
    Item2(title = "text2"),
    Item3(title = "text3"),
    Item4(title = "text4"),
    Item5(title = "text5"),
    Item6(title = "text6"),
}
