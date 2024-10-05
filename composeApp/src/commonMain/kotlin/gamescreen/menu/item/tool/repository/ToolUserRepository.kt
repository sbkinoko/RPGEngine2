package gamescreen.menu.item.tool.repository

import gamescreen.menu.item.user.UserRepository

class ToolUserRepository : UserRepository {
    override var userId: Int = UserRepository.TOOL_USER_INIT
}
