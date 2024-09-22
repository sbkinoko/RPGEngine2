package core

import core.confim.ConfirmViewModel
import core.confim.repository.ConfirmRepository
import org.koin.dsl.module

val CoreModule = module {
    single<ConfirmRepository> {
        ConfirmRepository()
    }

    single<ConfirmViewModel> {
        ConfirmViewModel()
    }

}
