package main.repository.skill

import battle.domain.AttackSkill
import battle.domain.HealSkill
import battle.domain.Skill
import battle.domain.TargetType

const val ATTACK_TO_2 = 0
const val CANT_USE = 1
const val ATTACK_NORMAL = 2
const val HEAL_SKILL = 3
const val REVIVE_SKILL = 4

class SkillRepositoryImpl : SkillRepository {

    override fun getSkill(id: Int): Skill {
        return when (id) {
            ATTACK_TO_2 -> AttackSkill(
                name = "２体攻撃",
                damageAmount = 10,
                needMP = 1,
                targetNum = 2,
            )

            CANT_USE -> AttackSkill(
                name = "使えないよ",
                damageAmount = 0,
                needMP = 9999,
                targetNum = 1,
                canUse = { false },
            )

            ATTACK_NORMAL -> AttackSkill(
                name = "通常攻撃",
                damageAmount = 1,
                needMP = 0,
                targetNum = 1,
            )

            HEAL_SKILL -> HealSkill(
                name = "回復",
                healAmount = 10,
                needMP = 1,
                targetNum = 1,
                targetType = TargetType.ACTIVE
            )

            REVIVE_SKILL -> HealSkill(
                name = "復活",
                healAmount = 10,
                needMP = 1,
                targetNum = 1,
                targetType = TargetType.INACTIVE
            )

            else -> throw NotImplementedError()
        }
    }
}