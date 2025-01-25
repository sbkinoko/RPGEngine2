package gamescreen.map

import gamescreen.map.domain.Player
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.backgroundcell.BackgroundRepositoryImpl
import gamescreen.map.repository.collision.CollisionRepository
import gamescreen.map.repository.collision.CollisionRepositoryImpl
import gamescreen.map.repository.npc.NPCRepository
import gamescreen.map.repository.npc.NPCRepositoryImpl
import gamescreen.map.repository.player.PlayerPositionRepository
import gamescreen.map.repository.player.PlayerPositionRepositoryImpl
import gamescreen.map.repository.playercell.PlayerCellRepository
import gamescreen.map.repository.playercell.PlayerCellRepositoryImpl
import gamescreen.map.service.velocitymanage.VelocityManageService
import gamescreen.map.service.velocitymanage.VelocityManageServiceImpl
import gamescreen.map.usecase.GetScreenCenterUseCase
import gamescreen.map.usecase.PlayerMoveManageUseCase
import gamescreen.map.usecase.PlayerMoveToUseCase
import gamescreen.map.usecase.PlayerMoveUseCase
import gamescreen.map.usecase.UpdateCellContainPlayerUseCase
import gamescreen.map.usecase.battledecidemonster.DecideBattleMonsterUseCase
import gamescreen.map.usecase.battledecidemonster.DecideBattleMonsterUseCaseImpl
import gamescreen.map.usecase.battlestart.StartBattleUseCase
import gamescreen.map.usecase.battlestart.StartBattleUseCaseImpl
import gamescreen.map.usecase.collision.geteventtype.GetEventTypeUseCase
import gamescreen.map.usecase.collision.geteventtype.GetEventTypeUseCaseImpl
import gamescreen.map.usecase.collision.iscollided.IsCollidedUseCase
import gamescreen.map.usecase.collision.iscollided.IsCollidedUseCaseImpl
import gamescreen.map.usecase.collision.list.GetCollisionListUseCase
import gamescreen.map.usecase.collision.list.GetCollisionListUseCaseImpl
import gamescreen.map.usecase.decideconnectcype.DecideConnectTypeUseCase
import gamescreen.map.usecase.decideconnectcype.DecideConnectTypeUseCaseImpl
import gamescreen.map.usecase.event.actionevent.ActionEventUseCase
import gamescreen.map.usecase.event.actionevent.ActionEventUseCaseImpl
import gamescreen.map.usecase.event.cellevent.CellEventUseCase
import gamescreen.map.usecase.event.cellevent.CellEventUseCaseImpl
import gamescreen.map.usecase.move.MoveBackgroundUseCase
import gamescreen.map.usecase.move.MoveBackgroundUseCaseImpl
import gamescreen.map.usecase.resetnpc.ResetNPCPositionUseCase
import gamescreen.map.usecase.resetnpc.ResetNPCPositionUseCaseImpl
import gamescreen.map.usecase.resetposition.ResetBackgroundPositionUseCase
import gamescreen.map.usecase.resetposition.ResetBackgroundPositionUseCaseImpl
import gamescreen.map.usecase.roadmap.RoadMapUseCase
import gamescreen.map.usecase.roadmap.RoadMapUseCaseImpl
import gamescreen.map.usecase.setplayercenter.SetPlayerCenterUseCase
import gamescreen.map.usecase.setplayercenter.SetPlayerCenterUseCaseImpl
import gamescreen.map.usecase.settalk.SetTalkUseCase
import gamescreen.map.usecase.settalk.SetTalkUseCaseImpl
import gamescreen.map.viewmodel.MapViewModel
import org.koin.dsl.module

val ModuleMap = module {
    single {
        MapViewModel()
    }

    single {
        Player(
            MapViewModel.VIRTUAL_PLAYER_SIZE,
        )
    }

    single<PlayerPositionRepository> {
        PlayerPositionRepositoryImpl()
    }

    single<BackgroundRepository> {
        BackgroundRepositoryImpl()
    }

    single<PlayerCellRepository> {
        PlayerCellRepositoryImpl()
    }

    single<CollisionRepository> {
        CollisionRepositoryImpl()
    }

    single<NPCRepository> {
        NPCRepositoryImpl()
    }

    single {
        PlayerMoveUseCase(
            playerPositionRepository = get(),
        )
    }

    single {
        PlayerMoveToUseCase(
            playerPositionRepository = get(),
        )
    }

    single<MoveBackgroundUseCase> {
        MoveBackgroundUseCaseImpl(
            backgroundRepository = get(),
            npcRepository = get(),
        )
    }

    single<ResetBackgroundPositionUseCase> {
        ResetBackgroundPositionUseCaseImpl(
            backgroundRepository = get(),
        )
    }

    single<ResetNPCPositionUseCase> {
        ResetNPCPositionUseCaseImpl(
            npcRepository = get(),
        )
    }

    single {
        UpdateCellContainPlayerUseCase(
            playerPositionRepository = get(),
            playerCellRepository = get(),
            backgroundRepository = get(),
        )
    }

    single {
        GetScreenCenterUseCase(
            backgroundRepository = get(),
        )
    }

    single {
        PlayerMoveManageUseCase(
            playerPositionRepository = get(),
            isCollidedUseCase = get(),
        )
    }

    single<VelocityManageService> {
        VelocityManageServiceImpl()
    }

    single<GetCollisionListUseCase> {
        GetCollisionListUseCaseImpl(
            collisionRepository = get(),
        )
    }

    single<IsCollidedUseCase> {
        IsCollidedUseCaseImpl(
            backgroundRepository = get(),
            getCollisionListUseCase = get(),
            npcRepository = get(),
        )
    }

    single<GetEventTypeUseCase> {
        GetEventTypeUseCaseImpl(
            backgroundRepository = get(),
            npcRepository = get(),
            getCollisionListUseCase = get(),
        )
    }

    single<DecideBattleMonsterUseCase> {
        DecideBattleMonsterUseCaseImpl(
            monsterRepository = get(),
        )
    }

    single<StartBattleUseCase> {
        StartBattleUseCaseImpl(
            battleMonsterRepository = get(),
            screenTypeRepository = get(),
            commandStateRepository = get(),
            actionRepository = get(),
        )
    }

    single<ActionEventUseCase> {
        ActionEventUseCaseImpl(
            textRepository = get(),
            addToolUseCase = get(),
            setShopItemUseCase = get(),
            setTalkUseCase = get(),
        )
    }

    single<SetTalkUseCase> {
        SetTalkUseCaseImpl(
            textRepository = get(),
            choiceRepository = get(),
        )
    }

    single<CellEventUseCase> {
        CellEventUseCaseImpl(
            backgroundRepository = get(),
            roadMapDataUseCase = get(),
        )
    }

    single<RoadMapUseCase> {
        RoadMapUseCaseImpl(
            setPlayerCenterUseCase = get(),
            resetBackgroundPositionUseCase = get(),
            resetNPCPositionUseCase = get(),
            updateCellContainPlayerUseCase = get(),
        )
    }

    single<SetPlayerCenterUseCase> {
        SetPlayerCenterUseCaseImpl(
            player = get(),
            getScreenCenterUseCase = get(),
            playerMoveToUseCase = get(),
        )
    }

    single<DecideConnectTypeUseCase> {
        DecideConnectTypeUseCaseImpl()
    }
}
