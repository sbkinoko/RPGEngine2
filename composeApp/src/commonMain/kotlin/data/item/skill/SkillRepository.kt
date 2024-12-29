package data.item.skill

import core.domain.item.skill.Skill
import data.item.ItemRepository

interface SkillRepository : ItemRepository {

    override fun getItem(id: Int): Skill

}
