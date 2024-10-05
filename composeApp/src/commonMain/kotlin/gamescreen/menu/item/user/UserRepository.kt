package gamescreen.menu.item.user

interface UserRepository {
    var userId: Int

    companion object {
        const val TOOL_USER_INIT = 0
    }
}
