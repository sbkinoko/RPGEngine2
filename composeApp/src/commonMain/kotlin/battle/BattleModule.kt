package battle

import battle.repository.ActionRepository
import battle.repository.BattleMonsterRepository
import battle.repositoryimpl.ActionRepositoryImpl
import battle.repositoryimpl.BattleMonsterRepositoryImpl
import battle.service.AttackService
import battle.service.FindTargetService
import battle.serviceimpl.AttackMonsterService
import battle.serviceimpl.FindTargetServiceImpl
import battle.usecase.AttackUseCase
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

    single<BattleMonsterRepository> {
        BattleMonsterRepositoryImpl()
    }

    single<AttackUseCase> {
        AttackUseCase(
            battleMonsterRepository = get(),
            findTargetService = get(),
            attackService = get(),
        )
    }
}
