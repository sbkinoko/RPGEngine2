package data.item.skill

import core.domain.Place
import core.domain.item.TargetType
import core.domain.item.skill.AttackSkill
import core.domain.item.skill.HealSkill
import core.domain.item.skill.Skill

const val ATTACK_TO_2 = 0
const val CANT_USE = 1
const val ATTACK_NORMAL = 2
const val HEAL_SKILL = 3
const val REVIVE_SKILL = 4
const val MONSTER_ATTACK_2 = 5

class SkillRepositoryImpl : SkillRepository {

    override fun getItem(id: Int): Skill {
        return when (id) {
            ATTACK_TO_2 -> AttackSkill(
                id = id,
                name = "２体攻撃",
                damageAmount = 10,
                needMP = 1,
                targetNum = 2,
                usablePlace = Place.BATTLE
            )

            CANT_USE -> AttackSkill(
                id = id,
                name = "使えないよ",
                damageAmount = 0,
                needMP = 9999,
                targetNum = 1,
                usablePlace = Place.NEITHER,
            )

            ATTACK_NORMAL -> AttackSkill(
                id = id,
                name = "通常攻撃",
                damageAmount = 20,
                needMP = 0,
                targetNum = 1,
                usablePlace = Place.BATTLE,
            )

            HEAL_SKILL -> HealSkill(
                id = id,
                name = "回復",
                healAmount = 10,
                needMP = 1,
                targetNum = 1,
                targetType = TargetType.ACTIVE,
                usablePlace = Place.BOTH,
            )

            REVIVE_SKILL -> HealSkill(
                id = id,
                name = "復活",
                healAmount = 10,
                needMP = 1,
                targetNum = 1,
                targetType = TargetType.INACTIVE,
                usablePlace = Place.BOTH,
            )

            MONSTER_ATTACK_2 -> AttackSkill(
                id = id,
                name = "通常攻撃2",
                damageAmount = 10,
                needMP = 0,
                targetNum = 1,
                usablePlace = Place.BATTLE,
            )

            else -> throw NotImplementedError()
        }
    }
}
