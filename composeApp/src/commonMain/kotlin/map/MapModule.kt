package map

import map.domain.Player
import map.manager.MoveManager
import map.repository.player.PlayerRepository
import map.repository.player.PlayerRepositoryImpl
import map.usecase.PlayerMoveToUseCase
import map.usecase.PlayerMoveUseCase
import map.viewmodel.MapViewModel
import org.koin.dsl.module

val MapModule = module {
    single {
        Player(
            MapViewModel.VIRTUAL_PLAYER_SIZE,
            playerRepository = get(),
            playerMoveUseCase = get(),
            playerMoveToUseCase = get(),
        )
    }

    single<PlayerRepository> {
        PlayerRepositoryImpl()
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
        MoveManager(
            playerRepository = get(),
        )
    }
}
