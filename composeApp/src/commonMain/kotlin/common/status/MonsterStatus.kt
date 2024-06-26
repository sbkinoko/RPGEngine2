package common.status

import common.status.param.HP
import common.status.param.MP

// fixme IDだけ入れれば画像とモンスター名を引っ張って来れるようにする
data class MonsterStatus(
    val imgId: Int,
    override var name: String,
    override val hp: HP,
    override val mp: MP,
) : Status
