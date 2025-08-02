package gamescreen

import core.PlayerStatusRepositoryName
import core.repository.screentype.ScreenTypeRepository
import core.repository.screentype.ScreenTypeRepositoryImpl
import main.MainViewModel
import main.ScreenTypeManager
import org.koin.dsl.module

val ModuleMain = module {
    single<MainViewModel> {
        MainViewModel(
            statusRepository = get(),
            statusDataRepository = get(
                qualifier = PlayerStatusRepositoryName,
            ),
        )
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
            buyViewModel = get(),
            sellViewModel = get(),
            screenTypeRepository = get(),
        )
    }
}
