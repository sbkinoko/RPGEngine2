package battle.repository.action

import battle.domain.ActionData
import battle.domain.ActionType

interface ActionRepository {
    fun setAction(
        playerId: Int,
        actionType: ActionType,
        skillId: Int? = null,
    )

    fun setTarget(
        playerId: Int,
        target: Int,
    )

    fun setAlly(
        playerId: Int,
        allyId: Int,
    )

    fun getAction(
        playerId: Int
    ): ActionData

    fun getLastSelectAction(
        playerId: Int
    ): ActionType

    /**
     * 最後に選んだ技の状態は保存しておきたいが、
     * 対象は戦闘毎にリセットする必要がある
     */
    fun resetTarget()

    companion object {
        val INITIAL_TARGET = 0
    }
}
