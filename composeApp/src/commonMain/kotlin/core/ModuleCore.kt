package core

import core.repository.bag.BagRepository
import core.repository.bag.BagRepositoryImpl
import core.repository.battlemonster.BattleInfoRepository
import core.repository.battlemonster.BattleInfoRepositoryImpl
import core.repository.event.EventRepository
import core.repository.event.EventRepositoryImpl
import core.repository.mapuistate.MapUiStateRepository
import core.repository.mapuistate.MapUiStateRepositoryImpl
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
import core.usecase.equipment.EquipUseCase
import core.usecase.equipment.EquipUseCaseImpl
import core.usecase.fly.FlyUseCase
import core.usecase.fly.FlyUseCaseImpl
import core.usecase.heal.MaxHealUseCase
import core.usecase.heal.MaxHealUseCaseImpl
import core.usecase.item.checkcanuseskill.CheckCanUseSkillUseCase
import core.usecase.item.checkcanuseskill.CheckCanUseSkillUseCaseImpl
import core.usecase.item.useitem.UseItemUseCase
import core.usecase.item.useitem.UseSkillUseCaseImpl
import core.usecase.item.useitem.UseToolUseCaseImpl
import core.usecase.restart.RestartUseCase
import core.usecase.restart.RestartUseCaseImpl
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import core.usecase.updateparameter.UpdatePlayerStatusUseCaseImpl
import core.usecase.updateparameter.UpdateStatusUseCase
import core.usecase.updateparameter.UpdateStatusUseCaseImpl
import data.repository.item.equipment.EquipmentId
import data.repository.item.tool.ToolId

import gamescreen.menu.DecToolUseCaseName
import gamescreen.menu.usecase.gettoolid.GetToolIdUseCase
import gamescreen.menu.usecase.gettoolid.GetToolIdUseCaseImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val UpdatePlayer = "UpdatePlayer"

val UpdateEnemyUseCaseName = named("UpdateEnemyStatus")

val PlayerStatusRepositoryName = named("PlayerStatusRepository")

val EnemyStatusRepositoryName = named("EnemyStatusRepository")

val ToolBagRepositoryName = named("ToolBagRepository")

val EquipmentBagRepositoryName = named(
    "EquipmentBagRepository"
)

val UseToolUseCaseName_ = named(
    "UseToolUseCase"
)

val UseSkillUseCaseName = named(
    "UseSkillUseCase"
)

val ModuleCore = module {
    single<StatusDataRepository>(
        qualifier = PlayerStatusRepositoryName
    ) {
        StatusDataRepositoryImpl()
    }

    single<PlayerStatusRepository> {
        PlayerStatusRepositoryImpl(
            statusRepository = get(),
        )
    }

    single<MapUiStateRepository> {
        MapUiStateRepositoryImpl()
    }

    single<BattleInfoRepository> {
        BattleInfoRepositoryImpl()
    }

    single<StatusDataRepository>(
        qualifier = EnemyStatusRepositoryName
    ) {
        StatusDataRepositoryImpl()
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

    single<UpdatePlayerStatusUseCase> {
        UpdatePlayerStatusUseCaseImpl(
            statusRepository = get<PlayerStatusRepository>()
        )
    }

    single<UpdateStatusUseCase>(
        qualifier = named(UpdatePlayer)
    ) {
        UpdateStatusUseCaseImpl(
            statusDataRepository = get(
                qualifier = PlayerStatusRepositoryName
            ),
        )
    }

    single<UpdateStatusUseCase>(
        qualifier = UpdateEnemyUseCaseName,
    )
    {
        UpdateStatusUseCaseImpl(
            statusDataRepository = get(
                qualifier = EnemyStatusRepositoryName,
            ),
        )
    }

    single<UseItemUseCase>(
        qualifier = UseToolUseCaseName_
    ) {
        UseToolUseCaseImpl(
            toolRepository = get(),
            updatePlayerStatusUseCase = get(),
            updateStatusUseCase = get(
                qualifier = named(UpdatePlayer)
            ),
            decToolUseCase = get(
                qualifier = DecToolUseCaseName
            ),
            getToolIdUseCase = get(),

            flyUseCase = get(),
        )
    }

    single<UseItemUseCase>(qualifier = UseSkillUseCaseName) {
        UseSkillUseCaseImpl(
            playerStatusRepository = get(),
            skillRepository = get(),
            updateStatus = get(
                qualifier = named(UpdatePlayer)
            )
        )
    }

    single<FlyUseCase> {
        FlyUseCaseImpl(
            mapUiStateRepository = get(),
            changeHeightUseCase = get(),
        )
    }

    single<GetToolIdUseCase> {
        GetToolIdUseCaseImpl(
            playerStatusRepository = get(),
            bagRepository = get(
                qualifier = ToolBagRepositoryName,
            ),
        )
    }

    single<MaxHealUseCase> {
        MaxHealUseCaseImpl(
            playerStatusRepository = get(
                qualifier = PlayerStatusRepositoryName,
            ),
        )
    }

    single<RestartUseCase> {
        RestartUseCaseImpl(
            roadMapUseCase = get(),
            maxHealUseCase = get(),
        )
    }

    single<EquipUseCase> {
        EquipUseCaseImpl(
            playerStatusRepository = get(),
            statusDataRepository = get(
                qualifier = PlayerStatusRepositoryName,
            ),
            equipmentRepository = get()
        )
    }

    single<BagRepository<ToolId>>(
        qualifier = ToolBagRepositoryName,
    ) {
        BagRepositoryImpl()
    }

    single<BagRepository<EquipmentId>>(
        qualifier = EquipmentBagRepositoryName
    )
    {
        BagRepositoryImpl()
    }
}
