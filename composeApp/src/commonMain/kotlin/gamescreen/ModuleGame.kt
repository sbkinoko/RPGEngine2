package gamescreen

import core.repository.screentype.ScreenTypeRepository
import core.repository.screentype.ScreenTypeRepositoryImpl
import main.MainViewModel
import main.ScreenTypeManager
import org.koin.dsl.module

val ModuleMain = module {
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

            shopMenuRepository = get(),
            shopViewModel = get(),

            screenTypeRepository = get(),
        )
    }
}
