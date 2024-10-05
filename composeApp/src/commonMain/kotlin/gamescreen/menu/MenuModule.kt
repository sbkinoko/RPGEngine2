package menu

import gamescreen.menu.item.skill.list.SkillListViewModel
import gamescreen.menu.item.skill.repository.skilluser.SkillUserRepository
import gamescreen.menu.item.skill.repository.skilluser.SkillUserRepositoryImpl
import gamescreen.menu.item.skill.repository.useid.UseSkillIdRepository
import gamescreen.menu.item.skill.usecase.useskill.UseSkillUseCase
import gamescreen.menu.item.skill.usecase.useskill.UseSkillUseCaseImpl
import gamescreen.menu.item.skill.user.SkillUserViewModel
import menu.main.MainMenuViewModel
import menu.repository.menustate.MenuStateRepository
import menu.repository.menustate.MenuStateRepositoryImpl
import menu.skill.repository.target.TargetRepository
import menu.skill.repository.target.TargetRepositoryImpl
import menu.skill.repository.useid.UseSkillIdRepositoryImpl
import menu.skill.target.SkillTargetViewModel
import menu.status.StatusViewModel
import menu.usecase.backfield.CloseMenuUseCase
import menu.usecase.backfield.CloseMenuUseCaseImpl
import menu.usecase.getviewmodelbycommandtype.GetControllerByCommandTypeUseCase
import menu.usecase.getviewmodelbycommandtype.GetControllerByCommandTypeUseCaseImpl
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

    single<TargetRepository> {
        TargetRepositoryImpl()
    }

    single<GetControllerByCommandTypeUseCase> {
        GetControllerByCommandTypeUseCaseImpl(
            confirmRepository = get(),
            textRepository = get(),
            menuStateRepository = get(),
            mainMenuViewModel = get(),
            statusViewModel = get(),
            skillUserViewModel = get(),
            skillListViewModel = get(),
            skillTargetViewModel = get(),
            confirmViewModel = get(),
            textViewModel = get(),
        )
    }

    single<CloseMenuUseCase> {
        CloseMenuUseCaseImpl(
            menuStateRepository = get(),
            changeToMapUseCase = get(),
        )
    }

    single<UseSkillUseCase> {
        UseSkillUseCaseImpl(
            targetRepository = get(),
            skillUserRepository = get(),
            useSkillIdRepository = get(),
            skillRepository = get(),
            updateStatusService = get(),
        )
    }
}
