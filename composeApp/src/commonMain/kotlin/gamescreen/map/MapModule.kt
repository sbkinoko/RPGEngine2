package gamescreen.map

import gamescreen.map.domain.Player
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.backgroundcell.BackgroundRepositoryImpl
import gamescreen.map.repository.collision.CollisionRepository
import gamescreen.map.repository.collision.CollisionRepositoryImpl
import gamescreen.map.repository.player.PlayerPositionRepository
import gamescreen.map.repository.player.PlayerPositionRepositoryImpl
import gamescreen.map.repository.playercell.PlayerCellRepository
import gamescreen.map.repository.playercell.PlayerCellRepositoryImpl
import gamescreen.map.usecase.FindEventCellUseCase
import gamescreen.map.usecase.GetScreenCenterUseCase
import gamescreen.map.usecase.IsCollidedUseCase
import gamescreen.map.usecase.MoveBackgroundUseCase
import gamescreen.map.usecase.PlayerMoveManageUseCase
import gamescreen.map.usecase.PlayerMoveToUseCase
import gamescreen.map.usecase.PlayerMoveUseCase
import gamescreen.map.usecase.ResetBackgroundPositionUseCase
import gamescreen.map.usecase.VelocityManageUseCase
import gamescreen.map.usecase.decideconnectcype.DecideConnectTypeUseCase
import gamescreen.map.usecase.decideconnectcype.DecideConnectTypeUseCaseImpl
import gamescreen.map.usecase.startbattle.StartBattleUseCase
import gamescreen.map.usecase.startbattle.StartBattleUseCaseImpl
import gamescreen.map.viewmodel.MapViewModel
import org.koin.dsl.module

val MapModule = module {
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

    single {
        MoveBackgroundUseCase(
            repository = get(),
            collisionRepository = get(),
        )
    }

    single {
        ResetBackgroundPositionUseCase(
            repository = get(),
            collisionRepository = get(),
        )
    }

    single {
        FindEventCellUseCase(
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

    single {
        VelocityManageUseCase(
            playerPositionRepository = get(),
        )
    }

    single {
        IsCollidedUseCase(
            repository = get(),
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

    single<DecideConnectTypeUseCase> {
        DecideConnectTypeUseCaseImpl()
    }
}
