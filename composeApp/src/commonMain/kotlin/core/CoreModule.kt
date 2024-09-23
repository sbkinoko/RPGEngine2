package core

import core.confim.ConfirmViewModel
import core.confim.repository.ConfirmRepository
import core.text.TextViewModel
import core.text.repository.TextRepository
import core.text.repository.TextRepositoryImpl
import core.usecase.CheckCanUseSkillUseCase
import core.usecase.CheckCanUseSkillUseCaseImpl
import org.koin.dsl.module

val CoreModule = module {
    single<ConfirmRepository> {
        ConfirmRepository()
    }

    single<ConfirmViewModel> {
        ConfirmViewModel()
    }

    single<TextRepository> {
        TextRepositoryImpl()
    }

    single<TextViewModel> {
        TextViewModel()
    }

    single<CheckCanUseSkillUseCase> {
        CheckCanUseSkillUseCaseImpl(
            skillRepository = get(),
        )
    }
}
