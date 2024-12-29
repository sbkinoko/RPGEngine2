package data

import data.item.skill.SkillRepository
import data.item.skill.SkillRepositoryImpl
import data.item.tool.ToolRepository
import data.item.tool.ToolRepositoryImpl
import data.monster.MonsterRepository
import data.monster.MonsterRepositoryImpl
import org.koin.dsl.module

val ModuleData = module {
    single<SkillRepository> {
        SkillRepositoryImpl()
    }

    single<ToolRepository> {
        ToolRepositoryImpl()
    }

    single<MonsterRepository> {
        MonsterRepositoryImpl()
    }
}
