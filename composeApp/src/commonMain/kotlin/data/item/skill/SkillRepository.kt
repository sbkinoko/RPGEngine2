package data.item.skill

import core.domain.item.Skill
import data.item.ItemRepository

interface SkillRepository : ItemRepository<SkillId, Skill>
