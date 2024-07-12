package map

import map.domain.Player
import map.manager.MoveManager
import map.manager.VelocityManager
import map.repository.backgroundcell.BackgroundRepository
import map.repository.backgroundcell.BackgroundRepositoryImpl
import map.repository.player.PlayerRepository
import map.repository.player.PlayerRepositoryImpl
import map.repository.playercell.PlayerCellRepository
import map.repository.playercell.PlayerCellRepositoryImpl
import map.usecase.FindEventCellUseCase
import map.usecase.GetScreenCenterUseCase
import map.usecase.MoveBackgroundUseCase
import map.usecase.PlayerMoveToUseCase
import map.usecase.PlayerMoveUseCase
import map.usecase.ResetBackgroundPositionUseCase
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
            repository = get()
        )
    }

    single {
        ResetBackgroundPositionUseCase(
            repository = get()
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
        MoveManager(
            playerRepository = get(),
        )
    }

    single {
        VelocityManager(
            playerRepository = get(),
        )
    }
}
