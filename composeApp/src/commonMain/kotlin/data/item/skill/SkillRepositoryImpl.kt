package data.item.skill

import core.domain.Place
import core.domain.item.CostType
import core.domain.item.DamageType
import core.domain.item.Skill
import core.domain.item.TargetStatusType
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
                damageType = DamageType.AtkMultiple(1),
                costType = CostType.MP(9999),
                targetNum = 1,
                usablePlace = Place.NEITHER,
                targetType = TargetType.Enemy,
                targetStatusType = TargetStatusType.ACTIVE,
            )

            SkillId.AttackToTwo -> AttackSkill(
                name = "２体攻撃",
                damageType = DamageType.AtkMultiple(1),
                costType = CostType.MP(1),
                targetNum = 2,
                usablePlace = Place.BATTLE,
                targetType = TargetType.Enemy,
                targetStatusType = TargetStatusType.ACTIVE,
            )

            SkillId.Normal1 -> AttackSkill(
                name = "通常攻撃",
                damageType = DamageType.AtkMultiple(1),
                costType = CostType.MP(0),
                targetNum = 1,
                usablePlace = Place.BATTLE,
                targetType = TargetType.Enemy,
                targetStatusType = TargetStatusType.ACTIVE,
            )

            SkillId.Normal2 -> AttackSkill(
                name = "通常攻撃2",
                damageType = DamageType.AtkMultiple(2),
                costType = CostType.MP(0),
                targetNum = 1,
                usablePlace = Place.BATTLE,
                targetType = TargetType.Enemy,
                targetStatusType = TargetStatusType.ACTIVE,
            )

            SkillId.Heal -> HealSkill(
                name = "回復",
                healAmount = 10,
                costType = CostType.MP(1),
                targetNum = 1,
                targetStatusType = TargetStatusType.ACTIVE,
                usablePlace = Place.BOTH,
                targetType = TargetType.Ally,
            )

            SkillId.Revive -> HealSkill(
                name = "復活",
                healAmount = 10,
                costType = CostType.MP(1),
                targetNum = 1,
                targetStatusType = TargetStatusType.INACTIVE,
                usablePlace = Place.BOTH,
                targetType = TargetType.Ally,
            )

            SkillId.Paralysis -> ConditionSkill(
                name = "麻痺",
                costType = CostType.MP(1),
                targetNum = 1,
                usablePlace = Place.BATTLE,
                conditionType = ConditionType.Paralysis(),
                targetType = TargetType.Enemy,
                targetStatusType = TargetStatusType.ACTIVE,
            )

            SkillId.Poison -> ConditionSkill(
                name = "毒",
                costType = CostType.MP(1),
                targetNum = 1,
                usablePlace = Place.BATTLE,
                conditionType = ConditionType.Poison(),
                targetType = TargetType.Enemy,
                targetStatusType = TargetStatusType.ACTIVE,
            )
        }
    }
}
