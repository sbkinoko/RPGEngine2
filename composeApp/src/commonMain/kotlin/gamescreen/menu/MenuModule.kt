package gamescreen.menu

import gamescreen.menu.item.repository.useitemid.UseItemIdRepository
import gamescreen.menu.item.repository.useitemid.UseItemIdRepositoryImpl
import gamescreen.menu.item.repository.user.UserRepository
import gamescreen.menu.item.repository.user.UserRepositoryImpl
import gamescreen.menu.item.skill.list.SkillListViewModel
import gamescreen.menu.item.skill.repository.skilluser.SkillUserRepository
import gamescreen.menu.item.skill.repository.skilluser.SkillUserRepositoryImpl
import gamescreen.menu.item.skill.repository.target.TargetRepository
import gamescreen.menu.item.skill.repository.target.TargetRepositoryImpl
import gamescreen.menu.item.skill.repository.useid.UseSkillIdRepository
import gamescreen.menu.item.skill.repository.useid.UseSkillIdRepositoryImpl
import gamescreen.menu.item.skill.target.SkillTargetViewModel
import gamescreen.menu.item.skill.usecase.useskill.UseSkillUseCase
import gamescreen.menu.item.skill.usecase.useskill.UseSkillUseCaseImpl
import gamescreen.menu.item.skill.user.SkillUserViewModel
import gamescreen.menu.item.tool.list.ToolListViewModel
import gamescreen.menu.item.tool.user.ToolUserViewModel
import gamescreen.menu.main.MainMenuViewModel
import gamescreen.menu.repository.menustate.MenuStateRepository
import gamescreen.menu.repository.menustate.MenuStateRepositoryImpl
import gamescreen.menu.status.StatusViewModel
import gamescreen.menu.usecase.backfield.CloseMenuUseCase
import gamescreen.menu.usecase.backfield.CloseMenuUseCaseImpl
import gamescreen.menu.usecase.getviewmodelbycommandtype.GetControllerByCommandTypeUseCase
import gamescreen.menu.usecase.getviewmodelbycommandtype.GetControllerByCommandTypeUseCaseImpl
import org.koin.dsl.module

class Qualifier {
    companion object {
        const val TOOL_USER = "TOOL_USER"
    }
}

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

    single {
        ToolUserViewModel()
    }

    single {
        ToolListViewModel()
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

    single<UserRepository> {
        UserRepositoryImpl()
    }

    single<UseItemIdRepository> {
        UseItemIdRepositoryImpl()
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
            toolUserViewModel = get(),
            toolListViewModel = get(),
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
