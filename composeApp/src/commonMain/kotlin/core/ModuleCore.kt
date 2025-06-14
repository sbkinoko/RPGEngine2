package core

import core.domain.status.StatusType
import core.repository.battlemonster.BattleInfoRepository
import core.repository.battlemonster.BattleInfoRepositoryImpl
import core.repository.event.EventRepository
import core.repository.event.EventRepositoryImpl
import core.repository.money.MoneyRepository
import core.repository.money.MoneyRepositoryImpl
import core.repository.player.PlayerStatusRepository
import core.repository.player.PlayerStatusRepositoryImpl
import core.repository.statusdata.StatusDataRepository
import core.repository.statusdata.StatusDataRepositoryImpl
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
import core.usecase.updateparameter.UpdateStatusUseCase
import core.usecase.updateparameter.UpdateStatusUseCaseImpl
import gamescreen.menu.usecase.gettoolid.GetToolIdUseCase
import gamescreen.menu.usecase.gettoolid.GetToolIdUseCaseImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val UpdatePlayer = "UpdatePlayer"

val PlayerStatusRepositoryName = named("PlayerStatusRepository")

val ModuleCore = module {
    single<StatusDataRepository<StatusType.Player>>(
        qualifier = PlayerStatusRepositoryName
    ) {
        StatusDataRepositoryImpl()
    }

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

    single<UpdateStatusUseCase<StatusType.Player>>(
        qualifier = named(UpdatePlayer)
    ) {
        UpdateStatusUseCaseImpl(
            statusDataRepository = get(
                qualifier = PlayerStatusRepositoryName
            ),
        )
    }

    single<UseToolUseCase> {
        UseToolUseCaseImpl(
            toolRepository = get(),
            updatePlayerStatusUseCase = get(),
            updateStatusUseCase = get(
                qualifier = named(UpdatePlayer)
            ),
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
