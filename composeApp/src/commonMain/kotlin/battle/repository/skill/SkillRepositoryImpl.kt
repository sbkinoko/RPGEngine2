package battle.repository.skill

import battle.domain.Skill

class SkillRepositoryImpl : SkillRepository {
    override fun getSkill(id: Int): Skill {
        return when (id) {
            0 -> Skill(
                name = "２体攻撃",
                damage = 10,
                needMP = 1,
                targetNum = 2,
                canUse = { mp -> mp >= 1 }
            )

            1 -> Skill(
                name = "使えないよ",
                damage = 0,
                needMP = 9999,
                targetNum = 1,
                canUse = { false }
            )

            2 -> Skill(
                name = "通常攻撃",
                damage = 1,
                needMP = 0,
                targetNum = 1,
                canUse = { true }
            )

            else -> throw NotImplementedError()
        }
    }
}
