package gamescreen.battle

import gamescreen.battle.command.actionphase.ActionPhaseViewModel
import gamescreen.battle.command.escape.EscapeViewModel
import gamescreen.battle.command.item.skill.SkillCommandViewModel
import gamescreen.battle.command.item.tool.ToolCommandViewModel
import gamescreen.battle.command.main.BattleMainViewModel
import gamescreen.battle.command.playeraction.PlayerActionViewModel
import gamescreen.battle.command.selectally.SelectAllyViewModel
import gamescreen.battle.command.selectenemy.SelectEnemyViewModel
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.repository.action.ActionRepositoryImpl
import gamescreen.battle.repository.commandstate.CommandStateRepository
import gamescreen.battle.repository.commandstate.CommandStateRepositoryImpl
import gamescreen.battle.service.FindTargetService
import gamescreen.battle.service.FindTargetServiceImpl
import gamescreen.battle.usecase.IsAllMonsterNotActiveUseCase
import gamescreen.battle.usecase.attack.AttackFromEnemyUseCaseImpl
import gamescreen.battle.usecase.attack.AttackFromPlayerUseCaseImpl
import gamescreen.battle.usecase.attack.AttackUseCase
import gamescreen.battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import gamescreen.battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCaseImpl
import gamescreen.battle.usecase.findactivetarget.FindActiveTargetUseCase
import gamescreen.battle.usecase.findactivetarget.FindActiveTargetUseCaseImpl
import gamescreen.battle.usecase.getcontrollerbyscreentype.GetControllerByCommandTypeUseCase
import gamescreen.battle.usecase.getcontrollerbyscreentype.GetControllerByCommandTypeUseCaseImpl
import gamescreen.battle.usecase.gettargetnum.GetTargetNumUseCase
import gamescreen.battle.usecase.gettargetnum.GetTargetNumUseCaseImpl
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
        ToolCommandViewModel()
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
            toolCommandViewModel = get(),
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
            playerStatusRepository = get(),
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
