package battle

import battle.command.actionphase.ActionPhaseViewModel
import battle.command.escape.EscapeViewModel
import battle.command.main.BattleMainViewModel
import battle.command.playeraction.PlayerActionViewModel
import battle.command.selectally.SelectAllyViewModel
import battle.command.selectenemy.SelectEnemyViewModel
import battle.command.skill.SkillCommandViewModel
import battle.repository.action.ActionRepository
import battle.repository.action.ActionRepositoryImpl
import battle.repository.commandstate.CommandStateRepository
import battle.repository.commandstate.CommandStateRepositoryImpl
import battle.service.FindTargetService
import battle.serviceimpl.FindTargetServiceImpl
import battle.usecase.IsAllMonsterNotActiveUseCase
import battle.usecase.attack.AttackFromEnemyUseCaseImpl
import battle.usecase.attack.AttackFromPlayerUseCaseImpl
import battle.usecase.attack.AttackUseCase
import battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCaseImpl
import battle.usecase.convertscreentypetocontroller.GetControllerByCommandTypeUseCase
import battle.usecase.convertscreentypetocontroller.GetControllerByCommandTypeUseCaseImpl
import battle.usecase.findactivetarget.FindActiveTargetUseCase
import battle.usecase.findactivetarget.FindActiveTargetUseCaseImpl
import battle.usecase.gettargetnum.GetTargetNumUseCase
import battle.usecase.gettargetnum.GetTargetNumUseCaseImpl
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

    single {
        SelectAllyViewModel()
    }

    single<ActionRepository> {
        ActionRepositoryImpl()
    }

    single<FindTargetService> {
        FindTargetServiceImpl()
    }

    single<CommandStateRepository> {
        CommandStateRepositoryImpl()
    }

    single<GetControllerByCommandTypeUseCase> {
        GetControllerByCommandTypeUseCaseImpl(
            commandStateRepository = get(),
            battleMainViewModel = get(),
            playerActionViewModel = get(),
            selectEnemyViewModel = get(),
            actionPhaseViewModel = get(),
            escapeViewModel = get(),
            skillCommandViewModel = get(),
            selectAllyViewModel = get(),
        )
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
