package main.repository.skill

import battle.domain.Skill

interface SkillRepository {

    fun getSkill(id: Int): Skill

}