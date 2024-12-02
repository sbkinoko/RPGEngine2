package core

import core.repository.battlemonster.BattleMonsterRepository
import core.repository.battlemonster.BattleMonsterRepositoryImpl
import core.repository.item.skill.SkillRepository
import core.repository.item.skill.SkillRepositoryImpl
import core.repository.item.tool.ToolRepository
import core.repository.item.tool.ToolRepositoryImpl
import core.repository.player.PlayerStatusRepository
import core.repository.player.PlayerStatusRepositoryImpl
import core.usecase.changetomap.ChangeToMapUseCase
import core.usecase.changetomap.ChangeToMapUseCaseImpl
import core.usecase.item.checkcanuseskill.CheckCanUseSkillUseCase
import core.usecase.item.checkcanuseskill.CheckCanUseSkillUseCaseImpl
import core.usecase.item.usetool.UseToolUseCase
import core.usecase.item.usetool.UseToolUseCaseImpl
import core.usecase.updateparameter.UpdateMonsterStatusUseCase
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import core.usecase.updateparameter.UpdatePlayerStatusUseCaseImpl
import gamescreen.menu.usecase.gettoolid.GetToolIdUseCase
import gamescreen.menu.usecase.gettoolid.GetToolIdUseCaseImpl
import org.koin.dsl.module

val CoreModule = module {
    single<PlayerStatusRepository> {
        PlayerStatusRepositoryImpl()
    }

    single<SkillRepository> {
        SkillRepositoryImpl()
    }

    single<BattleMonsterRepository> {
        BattleMonsterRepositoryImpl()
    }

    single<ToolRepository> {
        ToolRepositoryImpl()
    }

    single<CheckCanUseSkillUseCase> {
        CheckCanUseSkillUseCaseImpl(
            skillRepository = get(),
        )
    }

    single<ChangeToMapUseCase> {
        ChangeToMapUseCaseImpl(
            screenTypeRepository = get(),
        )
    }

    single {
        UpdateMonsterStatusUseCase(
            statusRepository = get<BattleMonsterRepository>()
        )
    }

    single<UpdatePlayerStatusUseCase> {
        UpdatePlayerStatusUseCaseImpl(
            statusRepository = get<PlayerStatusRepository>()
        )
    }

    single<UseToolUseCase> {
        UseToolUseCaseImpl(
            toolRepository = get(),
            updateStatusService = get(),
            decToolUseCase = get(),
            getToolIdUseCase = get(),
        )
    }

    single<GetToolIdUseCase> {
        GetToolIdUseCaseImpl(
            playerStatusRepository = get(),
            bagRepository = get(),
        )
    }
}
