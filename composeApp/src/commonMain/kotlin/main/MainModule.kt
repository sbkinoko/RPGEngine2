package main

import core.repository.screentype.ScreenTypeRepository
import core.repository.screentype.ScreenTypeRepositoryImpl
import org.koin.dsl.module

val MainModule = module {
    single<MainViewModel> {
        MainViewModel()
    }

    single<ScreenTypeRepository> {
        ScreenTypeRepositoryImpl()
    }
}
