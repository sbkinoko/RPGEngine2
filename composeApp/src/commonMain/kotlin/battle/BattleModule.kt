package battle

import battle.repository.action.ActionRepository
import battle.repository.action.ActionRepositoryImpl
import battle.repository.battlemonster.BattleMonsterRepository
import battle.repository.battlemonster.BattleMonsterRepositoryImpl
import battle.repository.commandstate.CommandStateRepository
import battle.repository.commandstate.CommandStateRepositoryImpl
import battle.repository.skill.SkillRepository
import battle.repository.skill.SkillRepositoryImpl
import battle.service.FindTargetService
import battle.service.attack.DecHpService
import battle.service.attack.DecMonsterHpService
import battle.service.attack.DecPlayerHpService
import battle.serviceimpl.FindTargetServiceImpl
import battle.usecase.IsAllMonsterNotActiveUseCase
import battle.usecase.attack.AttackFromEnemyUseCaseImpl
import battle.usecase.attack.AttackFromPlayerUseCaseImpl
import battle.usecase.attack.AttackUseCase
import battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCaseImpl
import battle.usecase.decmp.DecMpUseCase
import battle.usecase.decmp.DecMpUseCaseImpl
import battle.usecase.findactivetarget.FindActiveTargetUseCase
import battle.usecase.findactivetarget.FindActiveTargetUseCaseImpl
import battle.usecase.gettargetnum.GetTargetNumUseCase
import battle.usecase.gettargetnum.GetTargetNumUseCaseImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val EnemyAttackQualifier = "EnemyAttack"
const val PlayerAttackQualifier = "PlayerAttack"

val BattleModule = module {
    single<ActionRepository> {
        ActionRepositoryImpl()
    }

    single<DecHpService>(qualifier = named(PlayerAttackQualifier)) {
        DecMonsterHpService()
    }

    single<DecHpService>(qualifier = named(EnemyAttackQualifier)) {
        DecPlayerHpService()
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

    single<AttackUseCase>(
        qualifier = named(PlayerAttackQualifier),
    ) {
        AttackFromPlayerUseCaseImpl(
            battleMonsterRepository = get(),
            findTargetService = get(),
            attackMonsterService = get(
                qualifier = named(PlayerAttackQualifier)
            ),
        )
    }

    single<AttackUseCase>(
        qualifier = named(EnemyAttackQualifier),
    ) {
        AttackFromEnemyUseCaseImpl(
            playerRepository = get(),
            findTargetService = get(),
            attackPlayerService = get(
                qualifier = named(EnemyAttackQualifier)
            ),
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
            battleMonsterRepository = get(),
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
