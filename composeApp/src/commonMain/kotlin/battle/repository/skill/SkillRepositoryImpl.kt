package battle.repository.skill

import battle.domain.AttackSkill
import battle.domain.Skill

class SkillRepositoryImpl : SkillRepository {
    override fun getSkill(id: Int): Skill {
        return when (id) {
            0 -> AttackSkill(
                name = "２体攻撃",
                damage = 10,
                needMP = 1,
                targetNum = 2,
            )

            1 -> AttackSkill(
                name = "使えないよ",
                damage = 0,
                needMP = 9999,
                targetNum = 1,
                canUse = { false },
            )

            2 -> AttackSkill(
                name = "通常攻撃",
                damage = 1,
                needMP = 0,
                targetNum = 1,
            )

            else -> throw NotImplementedError()
        }
    }
}
