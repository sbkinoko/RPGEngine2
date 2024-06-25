package battle.repositoryimpl

import battle.domain.ActionData
import battle.repository.ActionRepository

class ActionRepositoryImpl : ActionRepository {
    private val actionMap: MutableMap<Int, ActionData> = mutableMapOf()

    override fun setAction(
        playerId: Int,
        target: List<Int>,
    ) {
        actionMap[playerId] = ActionData(target)
    }

    override fun getAction(playerId: Int): ActionData {
        return actionMap[playerId] ?: ActionData(listOf(0))
    }
}
