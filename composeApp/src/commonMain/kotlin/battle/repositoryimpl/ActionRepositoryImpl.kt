package battle.repositoryimpl

import battle.domain.ActionData
import battle.repository.ActionRepository

class ActionRepositoryImpl : ActionRepository {
    val actionMap: Map<Int, ActionData> = emptyMap()

    override fun setAction(playerId: Int, target: Int) {
        TODO("Not yet implemented")
    }

    override fun getAction(playerId: Int): ActionData {
        TODO("Not yet implemented")
    }
}
