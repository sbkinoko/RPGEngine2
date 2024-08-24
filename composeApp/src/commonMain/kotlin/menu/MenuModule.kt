package menu

import menu.repository.menustate.MenuStateRepository
import menu.repository.menustate.MenuStateRepositoryImpl
import menu.usecase.backfield.BackFieldUseCase
import menu.usecase.backfield.BackFieldUseCaseImpl
import org.koin.dsl.module

val MenuModule = module {
    single<MenuStateRepository> {
        MenuStateRepositoryImpl()
    }

    single<BackFieldUseCase> {
        BackFieldUseCaseImpl(
            screenTypeRepository = get(),
            menuStateRepository = get(),
        )
    }
}
