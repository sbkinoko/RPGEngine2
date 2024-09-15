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

// 暫定でmainMenuの操作を楽にするためにint→menuTypeの関数を作成
// fixme 直接menuTypeを入れる
fun Int.toMenuType() = when (this) {
    0 -> MenuType.Status
    1 -> MenuType.Item2
    2 -> MenuType.Item3
    3 -> MenuType.Item4
    4 -> MenuType.Item5
    5 -> MenuType.Item6
    else -> throw RuntimeException()
}
