package core.domain.status.monster

import core.domain.status.Character
import core.domain.status.DropItemInfo
import core.domain.status.StatusData
import core.domain.status.StatusType
import data.item.skill.SkillId

// fixme IDだけ入れれば画像とモンスター名を引っ張って来れるようにする
data class MonsterStatus(
    override val statusData: StatusData<StatusType.Enemy>,
    val imgId: Int,
    val exp: Int,
    val money: Int,
    val dropInfoList: List<DropItemInfo>,
    val skillList: List<SkillId>,
    val actionStyle: ActionStyle,
) : Character<StatusType.Enemy>
