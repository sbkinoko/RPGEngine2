package common

import common.repository.PlayerRepository
import common.repositoryImpl.PlayerRepositoryImpl
import org.koin.dsl.module

val CommonModule = module {
    single<PlayerRepository> {
        PlayerRepositoryImpl()
    }
}
