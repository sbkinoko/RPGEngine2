package core.repository.skill

import core.domain.Skill

interface SkillRepository {

    fun getSkill(id: Int): Skill

}
