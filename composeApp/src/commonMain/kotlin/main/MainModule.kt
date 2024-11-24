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

    single {
        ScreenTypeManager(
            mapViewModel = get(),
            battleViewModel = get(),
            menuViewModel = get(),

            choiceRepository = get(),
            choiceViewModel = get(),
            textRepository = get(),
            textViewModel = get(),

            screenTypeRepository = get(),
        )
    }
}
