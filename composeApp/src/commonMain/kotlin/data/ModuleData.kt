package data

import data.repository.monster.MonsterRepository
import data.repository.monster.MonsterRepositoryImpl
import data.repository.monster.item.equipment.EquipmentRepository
import data.repository.monster.item.equipment.EquipmentRepositoryImpl
import data.repository.monster.item.skill.SkillRepository
import data.repository.monster.item.skill.SkillRepositoryImpl
import data.repository.monster.item.tool.ToolRepository
import data.repository.monster.item.tool.ToolRepositoryImpl
import data.repository.status.StatusRepository
import data.repository.status.StatusRepositoryImpl
import org.koin.dsl.module

val ModuleData = module {
    single<SkillRepository> {
        SkillRepositoryImpl()
    }

    single<ToolRepository> {
        ToolRepositoryImpl()
    }

    single<EquipmentRepository> {
        EquipmentRepositoryImpl()
    }

    single<MonsterRepository> {
        MonsterRepositoryImpl()
    }

    single<StatusRepository> {
        StatusRepositoryImpl()
    }
}
