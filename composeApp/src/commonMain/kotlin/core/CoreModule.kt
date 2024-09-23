package core

import core.confim.ConfirmViewModel
import core.confim.repository.ConfirmRepository
import core.text.TextViewModel
import core.text.repository.TextRepository
import org.koin.dsl.module

val CoreModule = module {
    single<ConfirmRepository> {
        ConfirmRepository()
    }

    single<ConfirmViewModel> {
        ConfirmViewModel()
    }

    single<TextRepository> {
        TextRepository()
    }

    single<TextViewModel> {
        TextViewModel()
    }
}
