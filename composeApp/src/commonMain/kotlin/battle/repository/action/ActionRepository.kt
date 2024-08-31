package battle.repository.action

import battle.domain.ActionData
import battle.domain.ActionType

interface ActionRepository {
    // fixme 通常攻撃以外ができたら追加する
    fun setAction(

        playerId: Int,
        actionType: ActionType,
        target: List<Int>,
        skillId: Int? = null,
    )

    // fixme 通常攻撃以外ができたら技の情報を返す
    fun getAction(
        playerId: Int
    ): ActionData

    /**
     * 最後に選んだ技の状態は保存しておきたいが、
     * 対象は戦闘毎にリセットする必要がある
     */
    fun resetTarget()
}
