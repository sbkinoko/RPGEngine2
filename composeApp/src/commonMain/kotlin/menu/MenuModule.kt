package menu

import menu.main.MainMenuViewModel
import menu.repository.menustate.MenuStateRepository
import menu.repository.menustate.MenuStateRepositoryImpl
import menu.repository.skilluser.SkillUserRepository
import menu.repository.skilluser.SkillUserRepositoryImpl
import menu.skill.list.SkillListViewModel
import menu.skill.user.SkillUserViewModel
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
        SkillUserViewModel()
    }

    single {
        SkillListViewModel()
    }

    single<MenuStateRepository> {
        MenuStateRepositoryImpl()
    }

    single<SkillUserRepository> {
        SkillUserRepositoryImpl()
    }

    single<BackFieldUseCase> {
        BackFieldUseCaseImpl(
            screenTypeRepository = get(),
            menuStateRepository = get(),
        )
    }
}
