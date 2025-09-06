package core.repository.storage.tool

import core.domain.status.PlayerStatus
import data.repository.item.tool.ToolId

interface ToolDBRepository {

    fun setPlayers(players: List<PlayerStatus>)

    fun getTools(): List<List<ToolId>>
}
