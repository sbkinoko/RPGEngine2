package menu

import menu.main.MainMenuViewModel
import menu.repository.menustate.MenuStateRepository
import menu.repository.menustate.MenuStateRepositoryImpl
import menu.skill.list.SkillListViewModel
import menu.skill.repository.skilluser.SkillUserRepository
import menu.skill.repository.skilluser.SkillUserRepositoryImpl
import menu.skill.repository.useid.UseSkillIdRepository
import menu.skill.repository.useid.UseSkillIdRepositoryImpl
import menu.skill.target.SkillTargetViewModel
import menu.skill.usecase.GetSkillExplainUseCase
import menu.skill.usecase.GetSkillExplainUseCaseImpl
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

    single {
        SkillTargetViewModel()
    }

    single<MenuStateRepository> {
        MenuStateRepositoryImpl()
    }

    single<SkillUserRepository> {
        SkillUserRepositoryImpl()
    }

    single<UseSkillIdRepository> {
        UseSkillIdRepositoryImpl()
    }

    single<BackFieldUseCase> {
        BackFieldUseCaseImpl(
            screenTypeRepository = get(),
            menuStateRepository = get(),
        )
    }

    single<GetSkillExplainUseCase> {
        GetSkillExplainUseCaseImpl(
            skillRepository = get(),
        )
    }
}
