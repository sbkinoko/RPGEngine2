package core.domain.status

import core.domain.status.param.HP
import core.domain.status.param.MP

// fixme IDだけ入れれば画像とモンスター名を引っ張って来れるようにする
data class MonsterStatus(
    val imgId: Int,
    override var name: String,
    override val hp: HP,
    override val mp: MP,
    override val speed: Int,
    val exp: Int,
    val money: Int,
    val dropInfoList: List<DropItemInfo>,
) : Status
