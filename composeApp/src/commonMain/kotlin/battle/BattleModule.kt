package battle

import battle.repository.action.ActionRepository
import battle.repository.action.ActionRepositoryImpl
import battle.repository.battlemonster.BattleMonsterRepository
import battle.repository.battlemonster.BattleMonsterRepositoryImpl
import battle.repository.commandstate.CommandStateRepository
import battle.repository.commandstate.CommandStateRepositoryImpl
import battle.service.AttackService
import battle.service.FindTargetService
import battle.serviceimpl.AttackMonsterService
import battle.serviceimpl.FindTargetServiceImpl
import battle.usecase.AttackUseCase
import battle.usecase.IsAllMonsterNotActiveUseCase
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

    single<CommandStateRepository> {
        CommandStateRepositoryImpl()
    }

    single<AttackUseCase> {
        AttackUseCase(
            battleMonsterRepository = get(),
            findTargetService = get(),
            attackService = get(),
        )
    }

    single<IsAllMonsterNotActiveUseCase> {
        IsAllMonsterNotActiveUseCase(
            battleMonsterRepository = get(),
        )
    }
}
