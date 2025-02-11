package core.domain.status.monster

import core.domain.status.ConditionType
import core.domain.status.DropItemInfo
import core.domain.status.Status
import core.domain.status.param.HP
import core.domain.status.param.MP
import data.item.skill.SkillId

// fixme IDだけ入れれば画像とモンスター名を引っ張って来れるようにする
data class MonsterStatus(
    val imgId: Int,
    override var name: String,
    override val hp: HP,
    override val mp: MP,
    override val speed: Int,
    override val conditionList: List<ConditionType> = listOf(),
    val exp: Int,
    val money: Int,
    val dropInfoList: List<DropItemInfo>,
    val skillList: List<SkillId>,
    val actionStyle: ActionStyle,
) : Status
