package core

import core.repository.battlemonster.BattleInfoRepository
import core.repository.battlemonster.BattleInfoRepositoryImpl
import core.repository.event.EventRepository
import core.repository.event.EventRepositoryImpl
import core.repository.money.MoneyRepository
import core.repository.money.MoneyRepositoryImpl
import core.repository.player.PlayerStatusRepository
import core.repository.player.PlayerStatusRepositoryImpl
import core.service.CheckCanUseService
import core.service.CheckCanUseServiceImpl
import core.usecase.changetomap.ChangeToMapUseCase
import core.usecase.changetomap.ChangeToMapUseCaseImpl
import core.usecase.heal.MaxHealUseCase
import core.usecase.heal.MaxHealUseCaseImpl
import core.usecase.item.checkcanuseskill.CheckCanUseSkillUseCase
import core.usecase.item.checkcanuseskill.CheckCanUseSkillUseCaseImpl
import core.usecase.item.usetool.UseToolUseCase
import core.usecase.item.usetool.UseToolUseCaseImpl
import core.usecase.restart.RestartUseCase
import core.usecase.restart.RestartUseCaseImpl
import core.usecase.updateparameter.UpdateMonsterStatusUseCase
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import core.usecase.updateparameter.UpdatePlayerStatusUseCaseImpl
import gamescreen.menu.usecase.gettoolid.GetToolIdUseCase
import gamescreen.menu.usecase.gettoolid.GetToolIdUseCaseImpl
import org.koin.dsl.module

val ModuleCore = module {
    single<PlayerStatusRepository> {
        PlayerStatusRepositoryImpl(
            statusRepository = get(),
        )
    }

    single<BattleInfoRepository> {
        BattleInfoRepositoryImpl()
    }

    single<MoneyRepository> {
        MoneyRepositoryImpl()
    }

    single<EventRepository> {
        EventRepositoryImpl()
    }

    single<CheckCanUseSkillUseCase> {
        CheckCanUseSkillUseCaseImpl(
            skillRepository = get(),
            checkCanUseService = get(),
        )
    }

    single<CheckCanUseService> {
        CheckCanUseServiceImpl()
    }

    single<ChangeToMapUseCase> {
        ChangeToMapUseCaseImpl(
            screenTypeRepository = get(),
        )
    }

    single {
        UpdateMonsterStatusUseCase(
            statusRepository = get<BattleInfoRepository>()
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

    single<MaxHealUseCase> {
        MaxHealUseCaseImpl(
            playerStatusRepository = get(),
        )
    }

    single<RestartUseCase> {
        RestartUseCaseImpl(
            roadMapUseCase = get(),
            maxHealUseCase = get(),
        )
    }
}
