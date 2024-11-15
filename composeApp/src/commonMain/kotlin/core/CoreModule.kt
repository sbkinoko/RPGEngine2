package core

import core.confim.ChoiceViewModel
import core.repository.battlemonster.BattleMonsterRepository
import core.repository.battlemonster.BattleMonsterRepositoryImpl
import core.repository.item.skill.SkillRepository
import core.repository.item.skill.SkillRepositoryImpl
import core.repository.item.tool.ToolRepository
import core.repository.item.tool.ToolRepositoryImpl
import core.repository.player.PlayerStatusRepository
import core.repository.player.PlayerStatusRepositoryImpl
import core.text.TextViewModel
import core.text.repository.TextRepository
import core.text.repository.TextRepositoryImpl
import core.usecase.changetomap.ChangeToMapUseCase
import core.usecase.changetomap.ChangeToMapUseCaseImpl
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCase
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCaseImpl
import core.usecase.item.usetool.UseToolUseCase
import core.usecase.item.usetool.UseToolUseCaseImpl
import core.usecase.updateparameter.UpdateMonsterStatusUseCase
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import core.usecase.updateparameter.UpdatePlayerStatusUseCaseImpl
import org.koin.dsl.module

val CoreModule = module {
    single<ChoiceViewModel> {
        ChoiceViewModel()
    }

    single<TextViewModel> {
        TextViewModel()
    }

    single<core.confim.repository.ChoiceRepository> {
        core.confim.repository.ChoiceRepository()
    }

    single<TextRepository> {
        TextRepositoryImpl()
    }

    single<PlayerStatusRepository> {
        PlayerStatusRepositoryImpl()
    }

    single<SkillRepository> {
        SkillRepositoryImpl()
    }

    single<BattleMonsterRepository> {
        BattleMonsterRepositoryImpl()
    }

    single<ToolRepository> {
        ToolRepositoryImpl()
    }

    single<CheckCanUseSkillUseCase> {
        CheckCanUseSkillUseCaseImpl(
            skillRepository = get(),
        )
    }

    single<ChangeToMapUseCase> {
        ChangeToMapUseCaseImpl(
            screenTypeRepository = get(),
        )
    }

    single {
        UpdateMonsterStatusUseCase(
            statusRepository = get<BattleMonsterRepository>()
        )
    }

    single<UpdatePlayerStatusUseCase> {
        UpdatePlayerStatusUseCaseImpl(
            statusRepository = get<PlayerStatusRepository>()
        )
    }

    single<UseToolUseCase> {
        UseToolUseCaseImpl(
            playerStatusRepository = get(),
            toolRepository = get(),
            updateStatusService = get(),
            decToolUseCase = get(),
        )
    }
}
