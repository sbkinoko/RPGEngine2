package gamescreen.map

import gamescreen.map.domain.Player
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.backgroundcell.BackgroundRepositoryImpl
import gamescreen.map.repository.collision.CollisionRepository
import gamescreen.map.repository.collision.CollisionRepositoryImpl
import gamescreen.map.repository.collisionevent.EventCollisionRepository
import gamescreen.map.repository.collisionevent.EventCollisionRepositoryImpl
import gamescreen.map.repository.encouter.EncounterRepository
import gamescreen.map.repository.encouter.EncounterRepositoryImpl
import gamescreen.map.repository.npc.NPCRepository
import gamescreen.map.repository.npc.NPCRepositoryImpl
import gamescreen.map.repository.playercell.PlayerCellRepository
import gamescreen.map.repository.playercell.PlayerCellRepositoryImpl
import gamescreen.map.repository.position.PositionRepository
import gamescreen.map.repository.position.PositionRepositoryImpl
import gamescreen.map.service.makefrontdata.MakeFrontDataServiceImpl
import gamescreen.map.service.makefrontdata.MakeFrontDateService
import gamescreen.map.service.velocitymanage.VelocityManageService
import gamescreen.map.service.velocitymanage.VelocityManageServiceImpl
import gamescreen.map.usecase.GetScreenCenterUseCase
import gamescreen.map.usecase.PlayerMoveManageUseCase
import gamescreen.map.usecase.PlayerMoveToUseCase
import gamescreen.map.usecase.battledecidemonster.DecideBattleMonsterUseCase
import gamescreen.map.usecase.battledecidemonster.DecideBattleMonsterUseCaseImpl
import gamescreen.map.usecase.battleevent.StartEventBattleUseCase
import gamescreen.map.usecase.battleevent.StartEventBattleUseCaseImpl
import gamescreen.map.usecase.battlenormal.StartNormalBattleUseCase
import gamescreen.map.usecase.battlenormal.StartNormalBattleUseCaseImpl
import gamescreen.map.usecase.battlestart.StartBattleUseCase
import gamescreen.map.usecase.battlestart.StartBattleUseCaseImpl
import gamescreen.map.usecase.changeheight.ChangeHeightUseCase
import gamescreen.map.usecase.changeheight.ChangeHeightUseCaseImpl
import gamescreen.map.usecase.collision.geteventtype.GetEventTypeUseCase
import gamescreen.map.usecase.collision.geteventtype.GetEventTypeUseCaseImpl
import gamescreen.map.usecase.collision.iscollided.IsCollidedUseCase
import gamescreen.map.usecase.collision.iscollided.IsCollidedUseCaseImpl
import gamescreen.map.usecase.collision.iscollidedevent.IsCollidedEventUseCase
import gamescreen.map.usecase.collision.iscollidedevent.IsCollidedEventUseCaseImpl
import gamescreen.map.usecase.collision.list.GetCollisionListUseCase
import gamescreen.map.usecase.collision.list.GetCollisionListUseCaseImpl
import gamescreen.map.usecase.decideconnectcype.DecideConnectTypeUseCase
import gamescreen.map.usecase.decideconnectcype.DecideConnectTypeUseCaseImpl
import gamescreen.map.usecase.event.actionevent.ActionEventUseCase
import gamescreen.map.usecase.event.actionevent.ActionEventUseCaseImpl
import gamescreen.map.usecase.event.cellevent.CellEventUseCase
import gamescreen.map.usecase.event.cellevent.CellEventUseCaseImpl
import gamescreen.map.usecase.move.MoveUseCase
import gamescreen.map.usecase.move.MoveUseCaseImpl
import gamescreen.map.usecase.movebackground.MoveBackgroundUseCase
import gamescreen.map.usecase.movebackground.MoveBackgroundUseCaseImpl
import gamescreen.map.usecase.movenpc.MoveNPCUseCase
import gamescreen.map.usecase.movenpc.MoveNPCUseCaseImpl
import gamescreen.map.usecase.movetootherheight.MoveToOtherHeightUseCase
import gamescreen.map.usecase.movetootherheight.MoveToOtherHeightUseCaseImpl
import gamescreen.map.usecase.resetnpc.ResetNPCPositionUseCase
import gamescreen.map.usecase.resetnpc.ResetNPCPositionUseCaseImpl
import gamescreen.map.usecase.resetposition.ResetBackgroundPositionUseCase
import gamescreen.map.usecase.resetposition.ResetBackgroundPositionUseCaseImpl
import gamescreen.map.usecase.roadmap.RoadMapUseCase
import gamescreen.map.usecase.roadmap.RoadMapUseCaseImpl
import gamescreen.map.usecase.save.SaveUseCase
import gamescreen.map.usecase.save.SaveUseCaseImpl
import gamescreen.map.usecase.setplayercenter.SetPlayerCenterUseCase
import gamescreen.map.usecase.setplayercenter.SetPlayerCenterUseCaseImpl
import gamescreen.map.usecase.settalk.SetTalkUseCase
import gamescreen.map.usecase.settalk.SetTalkUseCaseImpl
import gamescreen.map.usecase.updatecellcontainplayer.UpdateCellContainPlayerUseCase
import gamescreen.map.usecase.updatecellcontainplayer.UpdateCellContainPlayerUseCaseImpl
import gamescreen.map.viewmodel.MapViewModel
import org.koin.dsl.module

val ModuleMap = module {
    single {
        MapViewModel(
            encounterRepository = get(),
            moveUseCase = get(),
            startNormalBattleUseCase = get(),
            positionRepository = get(),
            saveUseCase = get(),
        )
    }

    single {
        Player(
            MapViewModel.VIRTUAL_PLAYER_SIZE,
        )
    }

    single<PositionRepository> {
        PositionRepositoryImpl()
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

    single<EventCollisionRepository> {
        EventCollisionRepositoryImpl()
    }

    single<NPCRepository> {
        NPCRepositoryImpl()
    }

    single<EncounterRepository> {
        EncounterRepositoryImpl()
    }

    single<MoveUseCase> {
        MoveUseCaseImpl(
            moveNPCUseCase = get(),
            npcRepository = get(),
            moveBackgroundUseCase = get(),
            getEventTypeUseCase = get(),
            velocityManageService = get(),
            updateCellContainPlayerUseCase = get(),
            makeFrontDateService = get(),
        )
    }

    single<ChangeHeightUseCase> {
        ChangeHeightUseCaseImpl()
    }


    single<MoveToOtherHeightUseCase> {
        MoveToOtherHeightUseCaseImpl(
            isCollidedUseCase = get(),
            moveUseCase = get(),
        )
    }

    single {
        PlayerMoveToUseCase()
    }

    single<MoveBackgroundUseCase> {
        MoveBackgroundUseCaseImpl(
            backgroundRepository = get(),
            collisionListUseCase = get(),
        )
    }

    single<MoveNPCUseCase> {
        MoveNPCUseCaseImpl()
    }

    single<ResetBackgroundPositionUseCase> {
        ResetBackgroundPositionUseCaseImpl(
            backgroundRepository = get(),
            collisionListUseCase = get(),
        )
    }

    single<ResetNPCPositionUseCase> {
        ResetNPCPositionUseCaseImpl()
    }

    single<UpdateCellContainPlayerUseCase> {
        UpdateCellContainPlayerUseCaseImpl(
            playerCellRepository = get(),
        )
    }

    single {
        GetScreenCenterUseCase(
            backgroundRepository = get(),
        )
    }

    single {
        PlayerMoveManageUseCase(
            isCollidedUseCase = get(),
        )
    }

    single<MakeFrontDateService> {
        MakeFrontDataServiceImpl()
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
        IsCollidedUseCaseImpl()
    }

    single<IsCollidedEventUseCase> {
        IsCollidedEventUseCaseImpl(
            eventCollisionRepository = get(),
        )
    }

    single<GetEventTypeUseCase> {
        GetEventTypeUseCaseImpl()
    }

    single<DecideBattleMonsterUseCase> {
        DecideBattleMonsterUseCaseImpl(
            monsterRepository = get(),
        )
    }

    single<StartBattleUseCase> {
        StartBattleUseCaseImpl(
            battleInfoRepository = get(),
            screenTypeRepository = get(),
            commandStateRepository = get(),
            actionRepository = get(),
            eventRepository = get(),
            flashRepository = get(),
            attackEffectRepository = get(),
        )
    }

    single<StartEventBattleUseCase> {
        StartEventBattleUseCaseImpl(
            battleDataRepository = get(),
            startBattleUseCase = get(),
        )
    }

    single<StartNormalBattleUseCase> {
        StartNormalBattleUseCaseImpl(
            playerCellRepository = get(),
            decideBattleMonsterUseCase = get(),
            startBattleUseCase = get(),
            textRepository = get(),
            restartUseCase = get(),
        )
    }

    single<ActionEventUseCase> {
        ActionEventUseCaseImpl(
            textRepository = get(),
            addToolUseCase = get(),
            setShopItemUseCase = get(),
            setTalkUseCase = get(),
            moveToOtherHeightUseCase = get(),
            changeHeightUseCase = get(),
        )
    }

    single<SetTalkUseCase> {
        SetTalkUseCaseImpl(
            textRepository = get(),
            choiceRepository = get(),
            startEventBattleUseCase = get(),
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
            moveToOtherHeightUseCase = get(),
            makeFrontDateService = get(),
        )
    }

    single<SetPlayerCenterUseCase> {
        SetPlayerCenterUseCaseImpl(
            getScreenCenterUseCase = get(),
            playerMoveToUseCase = get(),
        )
    }

    single<DecideConnectTypeUseCase> {
        DecideConnectTypeUseCaseImpl()
    }

    single<SaveUseCase> {
        SaveUseCaseImpl(
            playerCellRepository = get(),
            positionRepository = get(),
            backgroundRepository = get(),
        )
    }
}
