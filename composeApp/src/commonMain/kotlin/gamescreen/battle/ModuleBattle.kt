package gamescreen.battle

import gamescreen.battle.command.actionphase.ActionPhaseViewModel
import gamescreen.battle.command.escape.EscapeViewModel
import gamescreen.battle.command.finish.BattleFinishViewModel
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
import gamescreen.battle.service.findtarget.FindTargetService
import gamescreen.battle.service.findtarget.FindTargetServiceImpl
import gamescreen.battle.service.isannihilation.IsAnnihilationService
import gamescreen.battle.service.isannihilation.IsAnnihilationServiceImpl
import gamescreen.battle.usecase.attack.AttackFromEnemyUseCaseImpl
import gamescreen.battle.usecase.attack.AttackFromPlayerUseCaseImpl
import gamescreen.battle.usecase.attack.AttackUseCase
import gamescreen.battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import gamescreen.battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCaseImpl
import gamescreen.battle.usecase.findactivetarget.FindActiveTargetUseCase
import gamescreen.battle.usecase.findactivetarget.FindActiveTargetUseCaseImpl
import gamescreen.battle.usecase.getcontrollerbyscreentype.GetControllerByCommandTypeUseCase
import gamescreen.battle.usecase.getcontrollerbyscreentype.GetControllerByCommandTypeUseCaseImpl
import gamescreen.battle.usecase.getdroptool.GetDropToolUseCase
import gamescreen.battle.usecase.getdroptool.GetDropToolUseCaseImpl
import gamescreen.battle.usecase.getexp.GetExpUseCase
import gamescreen.battle.usecase.getexp.GetExpUseCaseImpl
import gamescreen.battle.usecase.getmoney.GetMoneyUseCase
import gamescreen.battle.usecase.getmoney.GetMoneyUseCaseImpl
import gamescreen.battle.usecase.gettargetnum.GetTargetNumUseCase
import gamescreen.battle.usecase.gettargetnum.GetTargetNumUseCaseImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val QualifierAttackFromEnemy = "EnemyAttack"
const val QualifierAttackFromPlayer = "PlayerAttack"

val ModuleBattle = module {
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

    single {
        BattleFinishViewModel()
    }

    single<ActionRepository> {
        ActionRepositoryImpl()
    }

    single<FindTargetService> {
        FindTargetServiceImpl()
    }

    single<IsAnnihilationService> {
        IsAnnihilationServiceImpl()
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
            battleFinishViewModel = get(),
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

    single<GetMoneyUseCase> {
        GetMoneyUseCaseImpl(
            battleMonsterRepository = get()
        )
    }

    single<GetExpUseCase> {
        GetExpUseCaseImpl(
            battleMonsterRepository = get(),
        )
    }

    single<GetDropToolUseCase> {
        GetDropToolUseCaseImpl(
            battleMonsterRepository = get(),
        )
    }
}
