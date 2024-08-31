package common

import common.repository.player.PlayerRepository
import common.repository.player.PlayerRepositoryImpl
import org.koin.dsl.module

val CommonModule = module {
    single<PlayerRepository> {
        PlayerRepositoryImpl()
    }
}
