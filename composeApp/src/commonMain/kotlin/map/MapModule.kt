package map

import map.domain.Player
import map.repository.backgroundcell.BackgroundRepository
import map.repository.backgroundcell.BackgroundRepositoryImpl
import map.repository.collision.CollisionRepository
import map.repository.collision.CollisionRepositoryImpl
import map.repository.player.PlayerRepository
import map.repository.player.PlayerRepositoryImpl
import map.repository.playercell.PlayerCellRepository
import map.repository.playercell.PlayerCellRepositoryImpl
import map.usecase.FindEventCellUseCase
import map.usecase.GetScreenCenterUseCase
import map.usecase.IsCollidedUseCase
import map.usecase.MoveBackgroundUseCase
import map.usecase.PlayerMoveManageUseCase
import map.usecase.PlayerMoveToUseCase
import map.usecase.PlayerMoveUseCase
import map.usecase.ResetBackgroundPositionUseCase
import map.usecase.VelocityManageUseCase
import map.usecase.startbattle.StartBattleUseCase
import map.usecase.startbattle.StartBattleUseCaseImpl
import map.viewmodel.MapViewModel
import org.koin.dsl.module

val MapModule = module {
    single {
        Player(
            MapViewModel.VIRTUAL_PLAYER_SIZE,
        )
    }

    single<PlayerRepository> {
        PlayerRepositoryImpl()
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
            playerRepository = get(),
        )
    }

    single {
        PlayerMoveToUseCase(
            playerRepository = get(),
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
            playerRepository = get(),
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
            playerRepository = get(),
            isCollidedUseCase = get(),
        )
    }

    single {
        VelocityManageUseCase(
            playerRepository = get(),
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
}
