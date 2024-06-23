package battle.repository

import battle.domain.ActionData

interface ActionRepository {
    // fixme 通常攻撃以外ができたら追加する
    fun setAction(
        playerId: Int,
        target: List<Int>,
    )

    // fixme 通常攻撃以外ができたら技の情報を返す
    fun getAction(
        playerId: Int
    ): ActionData
}
