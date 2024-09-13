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
import common.status.MonsterStatus
import common.status.PlayerStatus
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

    single<DecHpService<MonsterStatus>>(
        qualifier = named(QualifierAttackFromPlayer)
    ) {
        DecMonsterHpService()
    }

    single<DecHpService<PlayerStatus>>(
        qualifier = named(QualifierAttackFromEnemy)
    ) {
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
        qualifier = named(QualifierAttackFromPlayer),
    ) {
        AttackFromPlayerUseCaseImpl(
            battleMonsterRepository = get(),
            findTargetService = get(),
            attackMonsterService = get(
                qualifier = named(QualifierAttackFromPlayer)
            ),
        )
    }

    single<AttackUseCase>(
        qualifier = named(QualifierAttackFromEnemy),
    ) {
        AttackFromEnemyUseCaseImpl(
            playerRepository = get(),
            findTargetService = get(),
            attackPlayerService = get(
                qualifier = named(QualifierAttackFromEnemy)
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
