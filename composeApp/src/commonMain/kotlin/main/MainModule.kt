package main

import main.screentype.ScreenTypeRepository
import main.screentype.ScreenTypeRepositoryImpl
import org.koin.dsl.module

val MainModule = module {
    single<MainViewModel> {
        MainViewModel()
    }

    single<ScreenTypeRepository> {
        ScreenTypeRepositoryImpl()
    }
}
