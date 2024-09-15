package menu

import menu.main.MainMenuViewModel
import menu.repository.menustate.MenuStateRepository
import menu.repository.menustate.MenuStateRepositoryImpl
import menu.skill.SkillViewModel
import menu.status.StatusViewModel
import menu.usecase.backfield.BackFieldUseCase
import menu.usecase.backfield.BackFieldUseCaseImpl
import org.koin.dsl.module

val MenuModule = module {
    single {
        MenuViewModel()
    }

    single {
        MainMenuViewModel()
    }

    single {
        StatusViewModel()
    }

    single {
        SkillViewModel()
    }

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
