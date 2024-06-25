package map

import map.domain.Player
import map.viewmodel.MapViewModel
import org.koin.dsl.module

val MapModule = module {
    single {
        Player(MapViewModel.VIRTUAL_PLAYER_SIZE)
    }
}
