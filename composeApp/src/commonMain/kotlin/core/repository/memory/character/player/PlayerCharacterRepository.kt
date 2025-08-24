package core.repository.memory.character.player

import core.domain.status.PlayerStatus
import data.repository.item.skill.SkillId
import data.repository.item.tool.ToolId
import kotlinx.coroutines.flow.StateFlow

interface PlayerCharacterRepository : core.repository.memory.character.CharacterRepository<PlayerStatus> {
    val playerStatusFlow: StateFlow<List<PlayerStatus>>

    fun getTool(
        playerId: Int,
        index: Int,
    ): ToolId

    fun getSkill(
        playerId: Int,
        index: Int,
    ): SkillId
}
