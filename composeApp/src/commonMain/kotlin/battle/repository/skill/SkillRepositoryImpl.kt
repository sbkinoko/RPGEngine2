package battle.repository.skill

import battle.domain.AttackSkill
import battle.domain.HealSkill
import battle.domain.Skill
import battle.domain.TargetType

const val HEAL_SKILL = 3

class SkillRepositoryImpl : SkillRepository {

    override fun getSkill(id: Int): Skill {
        return when (id) {
            0 -> AttackSkill(
                name = "２体攻撃",
                damageAmount = 10,
                needMP = 1,
                targetNum = 2,
            )

            1 -> AttackSkill(
                name = "使えないよ",
                damageAmount = 0,
                needMP = 9999,
                targetNum = 1,
                canUse = { false },
            )

            2 -> AttackSkill(
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

            else -> throw NotImplementedError()
        }
    }
}
