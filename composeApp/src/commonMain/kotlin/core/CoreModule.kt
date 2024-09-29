package core

import core.confim.ConfirmViewModel
import core.confim.repository.ConfirmRepository
import core.repository.battlemonster.BattleMonsterRepository
import core.repository.battlemonster.BattleMonsterRepositoryImpl
import core.repository.player.PlayerRepository
import core.repository.player.PlayerRepositoryImpl
import core.repository.skill.SkillRepository
import core.repository.skill.SkillRepositoryImpl
import core.text.TextViewModel
import core.text.repository.TextRepository
import core.text.repository.TextRepositoryImpl
import core.usecase.changetomap.ChangeToMapUseCase
import core.usecase.changetomap.ChangeToMapUseCaseImpl
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCase
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCaseImpl
import core.usecase.updateparameter.UpdateMonsterStatusUseCase
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import org.koin.dsl.module

val CoreModule = module {
    single<ConfirmViewModel> {
        ConfirmViewModel()
    }

    single<TextViewModel> {
        TextViewModel()
    }

    single<ConfirmRepository> {
        ConfirmRepository()
    }

    single<TextRepository> {
        TextRepositoryImpl()
    }

    single<PlayerRepository> {
        PlayerRepositoryImpl()
    }

    single<SkillRepository> {
        SkillRepositoryImpl()
    }

    single<BattleMonsterRepository> {
        BattleMonsterRepositoryImpl()
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

    single {
        UpdatePlayerStatusUseCase(
            statusRepository = get<PlayerRepository>()
        )
    }
}
