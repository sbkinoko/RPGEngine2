package battle.repositoryimpl

import battle.domain.ActionData
import battle.repository.ActionRepository
import common.values.playerNum

class ActionRepositoryImpl : ActionRepository {
    val actionList = List(playerNum) {
        ActionData(
            id = it,
            target = 0,
        )
    }

    override fun setAction(playerId: Int, target: Int) {
        TODO("Not yet implemented")
    }

    override fun getAction(playerId: Int): ActionData {
        TODO("Not yet implemented")
    }
}
