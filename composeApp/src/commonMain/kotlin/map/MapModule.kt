package map

import map.domain.Player
import map.repository.player.PlayerRepository
import map.repository.player.PlayerRepositoryImpl
import map.usecase.PlayerMoveUseCase
import map.viewmodel.MapViewModel
import org.koin.dsl.module

val MapModule = module {
    single {
        Player(
            MapViewModel.VIRTUAL_PLAYER_SIZE,
            playerRepository = get(),
            playerMoveUseCase = get(),
        )
    }

    single<PlayerRepository> {
        PlayerRepositoryImpl()
    }

    single {
        PlayerMoveUseCase(
            playerRepository = get()
        )
    }
}
