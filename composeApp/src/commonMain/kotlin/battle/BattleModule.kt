package battle

import battle.repository.action.ActionRepository
import battle.repository.action.ActionRepositoryImpl
import battle.repository.battlemonster.BattleMonsterRepository
import battle.repository.battlemonster.BattleMonsterRepositoryImpl
import battle.repository.commandstate.CommandStateRepository
import battle.repository.commandstate.CommandStateRepositoryImpl
import battle.repository.skill.SkillRepository
import battle.repository.skill.SkillRepositoryImpl
import battle.service.AttackService
import battle.service.FindTargetService
import battle.serviceimpl.AttackMonsterService
import battle.serviceimpl.FindTargetServiceImpl
import battle.usecase.AttackUseCase
import battle.usecase.IsAllMonsterNotActiveUseCase
import battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCaseImpl
import battle.usecase.decmp.DecMpUseCase
import battle.usecase.decmp.DecMpUseCaseImpl
import battle.usecase.findactivetarget.FindActiveTargetUseCase
import battle.usecase.findactivetarget.FindActiveTargetUseCaseImpl
import battle.usecase.gettargetnum.GetTargetNumUseCase
import battle.usecase.gettargetnum.GetTargetNumUseCaseImpl
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

    single<SkillRepository> {
        SkillRepositoryImpl()
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

    single<FindActiveTargetUseCase> {
        FindActiveTargetUseCaseImpl(
            findTargetService = get(),
        )
    }

    single<DecMpUseCase> {
        DecMpUseCaseImpl(
            playerRepository = get(),
        )
    }

    single<GetTargetNumUseCase> {
        GetTargetNumUseCaseImpl(
            skillRepository = get(),
            actionRepository = get(),
        )
    }

    single<ChangeSelectingActionPlayerUseCase> {
        ChangeSelectingActionPlayerUseCaseImpl(
            commandStateRepository = get(),
        )
    }
}
