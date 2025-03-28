package data.item.skill

import core.domain.Place
import core.domain.item.CostType
import core.domain.item.Skill
import core.domain.item.TargetType
import core.domain.item.skill.AttackSkill
import core.domain.item.skill.ConditionSkill
import core.domain.item.skill.HealSkill
import core.domain.status.ConditionType

class SkillRepositoryImpl : SkillRepository {

    override fun getItem(id: SkillId): Skill {
        return when (id) {
            SkillId.NONE -> throw RuntimeException()

            SkillId.CantUse -> AttackSkill(
                name = "使えないよ",
                damageAmount = 0,
                costType = CostType.MP(9999),
                targetNum = 1,
                usablePlace = Place.NEITHER,
            )

            SkillId.AttackToTwo -> AttackSkill(
                name = "２体攻撃",
                damageAmount = 10,
                costType = CostType.MP(1),
                targetNum = 2,
                usablePlace = Place.BATTLE
            )

            SkillId.Normal1 -> AttackSkill(
                name = "通常攻撃",
                damageAmount = 20,
                costType = CostType.MP(0),
                targetNum = 1,
                usablePlace = Place.BATTLE,
            )

            SkillId.Normal2 -> AttackSkill(
                name = "通常攻撃2",
                damageAmount = 10,
                costType = CostType.MP(0),
                targetNum = 1,
                usablePlace = Place.BATTLE,
            )

            SkillId.Heal -> HealSkill(
                name = "回復",
                healAmount = 10,
                costType = CostType.MP(1),
                targetNum = 1,
                targetType = TargetType.ACTIVE,
                usablePlace = Place.BOTH,
            )

            SkillId.Revive -> HealSkill(
                name = "復活",
                healAmount = 10,
                costType = CostType.MP(1),
                targetNum = 1,
                targetType = TargetType.INACTIVE,
                usablePlace = Place.BOTH,
            )

            SkillId.Paralysis -> ConditionSkill(
                name = "麻痺",
                costType = CostType.MP(1),
                targetNum = 1,
                usablePlace = Place.BATTLE,
                conditionType = ConditionType.Paralysis(),
            )

            SkillId.Poison -> ConditionSkill(
                name = "毒",
                costType = CostType.MP(1),
                targetNum = 1,
                usablePlace = Place.BATTLE,
                conditionType = ConditionType.Poison(),
            )
        }
    }
}
