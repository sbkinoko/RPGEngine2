package main

import main.repository.screentype.ScreenTypeRepository
import main.repository.screentype.ScreenTypeRepositoryImpl
import main.viewmodel.MainViewModel
import org.koin.dsl.module

val MainModule = module {
    single<ScreenTypeRepository> {
        ScreenTypeRepositoryImpl()
    }

    single<MainViewModel> {
        MainViewModel()
    }

}
