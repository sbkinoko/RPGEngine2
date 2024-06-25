package battle

import battle.repository.ActionRepository
import battle.repositoryimpl.ActionRepositoryImpl
import battle.service.AttackService
import battle.service.FindTargetService
import battle.serviceimpl.AttackMonsterService
import battle.serviceimpl.FindTargetServiceImpl
import org.koin.dsl.module

val BattleModule = module {
    single<ActionRepository> {
        ActionRepositoryImpl()
    }

    single<AttackService> {
        AttackMonsterService()
    }

    single<FindTargetService> {
        FindTargetServiceImpl()
    }
}
