package gamescreen

import core.EquipmentBagRepositoryName
import core.PlayerStatusRepositoryName
import core.ToolBagRepositoryName
import core.repository.memory.screentype.ScreenTypeRepository
import core.repository.memory.screentype.ScreenTypeRepositoryImpl
import gamescreen.init.InitUseCase
import gamescreen.init.InitUseCaseImpl
import main.MainViewModel
import main.ScreenTypeManager
import org.koin.dsl.module

val ModuleMain = module {
    single<MainViewModel> {
        MainViewModel(
            initUseCase = get(),
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

    single<InitUseCase> {

        InitUseCaseImpl(
            equipmentBagRepository = get(EquipmentBagRepositoryName),
            toolBagRepository = get(ToolBagRepositoryName),
            statusRepository = get(),
            statusDataRepository = get(
                qualifier = PlayerStatusRepositoryName,
            ),
            moneyRepository = get(),
            moneyDBRepository = get(),
            playerDBRepository = get(),

            playerCharacterRepository = get(),
            toolDBRepository = get(),
        )
    }
}
