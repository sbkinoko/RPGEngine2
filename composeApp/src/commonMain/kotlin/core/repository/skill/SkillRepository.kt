package core.repository.skill

import core.domain.item.skill.Skill

interface SkillRepository {

    fun getSkill(id: Int): Skill

}
