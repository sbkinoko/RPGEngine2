package data.item.skill

import core.domain.item.Skill
import data.item.ItemRepository

interface SkillRepository : ItemRepository<SkillId> {

    override fun getItem(id: SkillId): Skill

}
