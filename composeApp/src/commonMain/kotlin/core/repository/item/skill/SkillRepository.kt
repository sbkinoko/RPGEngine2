package core.repository.item.skill

import core.domain.item.skill.Skill

interface SkillRepository {

    fun getSkill(id: Int): Skill

}
