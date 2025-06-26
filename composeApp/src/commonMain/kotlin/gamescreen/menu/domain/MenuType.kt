package gamescreen.menu.domain

enum class MenuType(
    val title: String = "",
) {
    Main,
    Status(
        title = "ステータス",
    ),
    SKILL_USER(
        title = "スキル"
    ),
    SKILL_LST,
    SKILL_TARGET,
    TOOL_USER(
        title = "道具"
    ),
    TOOL_LIST,
    TOOL_TARGET,
    TOOL_GIVE,

    EQUIPMENT_USER(title = "装備"),
    EQUIPMENT_LIST,
    EQUIPMENT_TARGET,

    Item3(title = "text3"),
    Collision(title = "当たり判定:"),
    Item5(title = "text5"),
    Item6(title = "text6"),
}

// 暫定でmainMenuの操作を楽にするためにint→menuTypeの関数を作成
// fixme 直接menuTypeを入れる
fun Int.toMenuType() = when (this) {
    0 -> MenuType.Status
    1 -> MenuType.SKILL_USER
    2 -> MenuType.TOOL_USER
    3 -> MenuType.Collision
    4 -> MenuType.EQUIPMENT_USER
    5 -> MenuType.Item6
    else -> throw RuntimeException()
}
