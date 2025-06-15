package core.domain.status.monster

import core.domain.status.DropItemInfo
import data.item.skill.SkillId

// fixme IDだけ入れれば画像とモンスター名を引っ張って来れるようにする
data class MonsterStatus(
    val imgId: Int,
    val exp: Int,
    val money: Int,
    val dropInfoList: List<DropItemInfo>,
    val skillList: List<SkillId>,
    val actionStyle: ActionStyle,
)
