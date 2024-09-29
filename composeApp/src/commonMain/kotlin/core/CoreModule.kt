package core

import core.confim.ConfirmViewModel
import core.confim.repository.ConfirmRepository
import core.repository.player.PlayerRepository
import core.repository.player.PlayerRepositoryImpl
import core.text.TextViewModel
import core.text.repository.TextRepository
import core.text.repository.TextRepositoryImpl
import core.usecase.changetomap.ChangeToMapUseCase
import core.usecase.changetomap.ChangeToMapUseCaseImpl
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCase
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCaseImpl
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
}
