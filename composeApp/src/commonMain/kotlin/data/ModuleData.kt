package data

import data.battle.BattleDataRepository
import data.battle.BattleDataRepositoryImpl
import data.item.skill.SkillRepository
import data.item.skill.SkillRepositoryImpl
import data.item.tool.ToolRepository
import data.item.tool.ToolRepositoryImpl
import data.monster.MonsterRepository
import data.monster.MonsterRepositoryImpl
import data.status.StatusRepository
import data.status.StatusRepositoryImpl
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

    single<BattleDataRepository> {
        BattleDataRepositoryImpl(
            monsterRepository = get(),
        )
    }

    single<StatusRepository> {
        StatusRepositoryImpl()
    }
}
