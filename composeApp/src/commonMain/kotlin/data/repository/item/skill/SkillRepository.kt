package data.repository.item.skill

import core.domain.item.Skill
import data.repository.item.ItemRepository

interface SkillRepository : ItemRepository<SkillId, Skill>
