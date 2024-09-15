package common

import main.repository.player.PlayerRepository
import main.repository.player.PlayerRepositoryImpl
import org.koin.dsl.module

val CommonModule = module {
    single<PlayerRepository> {
        PlayerRepositoryImpl()
    }
}
