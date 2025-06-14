package gamescreen.battle

import core.UpdatePlayer
import core.domain.status.StatusType
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
import gamescreen.battle.repository.attackeffect.AttackEffectRepository
import gamescreen.battle.repository.attackeffect.AttackEffectRepositoryImpl
import gamescreen.battle.repository.commandstate.CommandStateRepository
import gamescreen.battle.repository.commandstate.CommandStateRepositoryImpl
import gamescreen.battle.repository.flash.FlashRepository
import gamescreen.battle.repository.flash.FlashRepositoryImpl
import gamescreen.battle.service.attackcalc.AttackCalcService
import gamescreen.battle.service.attackcalc.AttackCalcServiceImpl
import gamescreen.battle.service.findtarget.FindTargetService
import gamescreen.battle.service.findtarget.FindTargetServiceImpl
import gamescreen.battle.service.isannihilation.IsAnnihilationService
import gamescreen.battle.service.isannihilation.IsAnnihilationServiceImpl
import gamescreen.battle.usecase.addexp.AddExpUseCase
import gamescreen.battle.usecase.addexp.AddExpUseCaseImpl
import gamescreen.battle.usecase.attack.AttackFromEnemyUseCaseImpl
import gamescreen.battle.usecase.attack.AttackFromPlayerUseCaseImpl
import gamescreen.battle.usecase.attack.AttackUseCase
import gamescreen.battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import gamescreen.battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCaseImpl
import gamescreen.battle.usecase.condition.ConditionFromEnemyUseCaseImpl
import gamescreen.battle.usecase.condition.ConditionFromPlayerUseCaseImpl
import gamescreen.battle.usecase.condition.ConditionUseCase
import gamescreen.battle.usecase.decideactionorder.DecideActionOrderUseCase
import gamescreen.battle.usecase.decideactionorder.DecideActionOrderUseCaseImpl
import gamescreen.battle.usecase.effect.EffectUseCase
import gamescreen.battle.usecase.effect.EffectUseCaseImpl
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
        BattleViewModel(
            flashRepository = get(),
            attackEffectInfoRepository = get(),

            statusDataRepository = get()
        )
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
        ActionPhaseViewModel(
            decideActionOrderUseCase = get(),
            statusDataRepository = get(),
        )
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
        BattleFinishViewModel(
            eventRepository = get(),
        )
    }

    single<FlashRepository> {
        FlashRepositoryImpl()
    }

    single<AttackEffectRepository> {
        AttackEffectRepositoryImpl()
    }

    single<ActionRepository> {
        ActionRepositoryImpl()
    }

    single<FindTargetService> {
        FindTargetServiceImpl()
    }

    single<AttackCalcService> {
        AttackCalcServiceImpl()
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

    single<DecideActionOrderUseCase> {
        DecideActionOrderUseCaseImpl()
    }

    single<AttackUseCase<StatusType.Player>>(
        qualifier = named(QualifierAttackFromPlayer)
    ) {
        AttackFromPlayerUseCaseImpl(
            battleInfoRepository = get(),
            findTargetService = get(),
            attackCalcService = get(),
            effectUseCase = get(),
        )
    }

    single<ConditionUseCase>(
        qualifier = named(QualifierAttackFromPlayer),
    ) {
        ConditionFromPlayerUseCaseImpl(
            battleInfoRepository = get(),
            findTargetService = get(),
            updateMonsterStatusService = get(),
        )
    }

    single<AttackUseCase<StatusType.Enemy>>(
        qualifier = named(QualifierAttackFromEnemy)
    )
    {
        AttackFromEnemyUseCaseImpl(
            statusDataRepository = get(),
            findTargetService = get(),
            attackCalcService = get(),
        )
    }

    single<EffectUseCase> {
        EffectUseCaseImpl(
            flashRepository = get(),
            attackEffectRepository = get(),
        )
    }

    single<ConditionUseCase>(
        qualifier = named(QualifierAttackFromEnemy),
    ) {
        ConditionFromEnemyUseCaseImpl(
            statusDataRepository = get(),
            findTargetService = get(),
            updatePlayerStatusService = get(
                qualifier = named(UpdatePlayer),
            ),
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
            battleInfoRepository = get()
        )
    }

    single<GetExpUseCase> {
        GetExpUseCaseImpl(
            battleInfoRepository = get(),
        )
    }

    single<GetDropToolUseCase> {
        GetDropToolUseCaseImpl(
            battleInfoRepository = get(),
        )
    }

    single<AddExpUseCase> {
        AddExpUseCaseImpl(
            playerStatusRepository = get(),
            statusRepository = get(),
        )
    }
}
