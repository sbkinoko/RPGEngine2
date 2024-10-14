package core.repository.item.skill

import core.domain.item.skill.Skill
import core.repository.item.ItemRepository

interface SkillRepository : ItemRepository {

    override fun getItem(id: Int): Skill

}
