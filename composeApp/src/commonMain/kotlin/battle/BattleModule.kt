package battle

import battle.command.actionphase.ActionPhaseViewModel
import battle.command.escape.EscapeViewModel
import battle.command.main.BattleMainViewModel
import battle.command.playeraction.PlayerActionViewModel
import battle.command.selectenemy.SelectEnemyViewModel
import battle.command.skill.SkillCommandViewModel
import battle.repository.action.ActionRepository
import battle.repository.action.ActionRepositoryImpl
import battle.repository.battlemonster.BattleMonsterRepository
import battle.repository.battlemonster.BattleMonsterRepositoryImpl
import battle.repository.commandstate.CommandStateRepository
import battle.repository.commandstate.CommandStateRepositoryImpl
import battle.repository.skill.SkillRepository
import battle.repository.skill.SkillRepositoryImpl
import battle.service.FindTargetService
import battle.serviceimpl.FindTargetServiceImpl
import battle.usecase.IsAllMonsterNotActiveUseCase
import battle.usecase.attack.AttackFromEnemyUseCaseImpl
import battle.usecase.attack.AttackFromPlayerUseCaseImpl
import battle.usecase.attack.AttackUseCase
import battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCaseImpl
import battle.usecase.findactivetarget.FindActiveTargetUseCase
import battle.usecase.findactivetarget.FindActiveTargetUseCaseImpl
import battle.usecase.gettargetnum.GetTargetNumUseCase
import battle.usecase.gettargetnum.GetTargetNumUseCaseImpl
import battle.usecase.updateparameter.UpdateMonsterStatusUseCase
import battle.usecase.updateparameter.UpdatePlayerStatusUseCase
import common.repository.player.PlayerRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val QualifierAttackFromEnemy = "EnemyAttack"
const val QualifierAttackFromPlayer = "PlayerAttack"

val BattleModule = module {
    single {
        BattleViewModel()
    }

    single {
        BattleMainViewModel()
    }

    single {
        PlayerActionViewModel()
    }

    single {
        SelectEnemyViewModel()
    }

    single {
        ActionPhaseViewModel()
    }

    single {
        EscapeViewModel()
    }

    single {
        SkillCommandViewModel()
    }

    single<ActionRepository> {
        ActionRepositoryImpl()
    }

    single {
        UpdateMonsterStatusUseCase(
            statusRepository = get<BattleMonsterRepository>()
        )
    }

    single {
        UpdatePlayerStatusUseCase(
            statusRepository = get<PlayerRepository>()
        )
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
        qualifier = named(QualifierAttackFromPlayer),
    ) {
        AttackFromPlayerUseCaseImpl(
            battleMonsterRepository = get(),
            findTargetService = get(),
            updateMonsterStatusService = get(),
        )
    }

    single<AttackUseCase>(
        qualifier = named(QualifierAttackFromEnemy),
    ) {
        AttackFromEnemyUseCaseImpl(
            playerRepository = get(),
            findTargetService = get(),
            updatePlayerStatusService = get(),
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
