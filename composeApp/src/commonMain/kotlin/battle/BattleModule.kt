package battle

import battle.repository.ActionRepository
import battle.repositoryimpl.ActionRepositoryImpl
import battle.service.AttackService
import battle.serviceimpl.AttackMonsterService
import org.koin.dsl.module

val BattleModule = module {
    single<ActionRepository> {
        ActionRepositoryImpl()
    }

    single<AttackService> {
        AttackMonsterService()
    }
}
