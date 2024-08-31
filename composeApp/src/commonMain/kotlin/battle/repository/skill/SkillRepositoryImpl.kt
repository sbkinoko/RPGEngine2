package battle.repository.skill

import battle.domain.Skill

class SkillRepositoryImpl : SkillRepository {
    override fun getSkill(id: Int): Skill {
        return when (id) {
            0 -> Skill(
                name = "２体攻撃",
                needMP = 1,
                targetNum = 2,
                canUse = { mp -> mp >= 1 }
            )

            1 -> Skill(
                name = "使えないよ",
                needMP = 9999,
                targetNum = 1,
                canUse = { false }
            )

            else -> throw NotImplementedError()
        }
    }
}
