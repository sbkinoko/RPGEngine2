package data.item.skill

import core.domain.item.ItemKind
import data.item.ItemRepository

interface SkillRepository : ItemRepository {

    override fun getItem(id: Int): ItemKind.Skill

}
