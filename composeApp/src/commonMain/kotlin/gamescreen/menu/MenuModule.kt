package gamescreen.menu

import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.item.repository.index.IndexRepositoryImpl
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.item.repository.target.TargetRepositoryImpl
import gamescreen.menu.item.repository.useitemid.UseItemIdRepository
import gamescreen.menu.item.repository.useitemid.UseItemIdRepositoryImpl
import gamescreen.menu.item.repository.user.UserRepository
import gamescreen.menu.item.repository.user.UserRepositoryImpl
import gamescreen.menu.item.skill.list.SkillListViewModel
import gamescreen.menu.item.skill.target.SkillTargetViewModel
import gamescreen.menu.item.skill.usecase.useskill.UseSkillUseCase
import gamescreen.menu.item.skill.usecase.useskill.UseSkillUseCaseImpl
import gamescreen.menu.item.skill.user.SkillUserViewModel
import gamescreen.menu.item.tool.list.ToolListViewModel
import gamescreen.menu.item.tool.target.ToolTargetViewModel
import gamescreen.menu.item.tool.user.ToolUserViewModel
import gamescreen.menu.main.MainMenuViewModel
import gamescreen.menu.repository.bag.BagRepository
import gamescreen.menu.repository.bag.BagRepositoryImpl
import gamescreen.menu.repository.menustate.MenuStateRepository
import gamescreen.menu.repository.menustate.MenuStateRepositoryImpl
import gamescreen.menu.status.StatusViewModel
import gamescreen.menu.usecase.backfield.CloseMenuUseCase
import gamescreen.menu.usecase.backfield.CloseMenuUseCaseImpl
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.menu.usecase.bag.addtool.AddToolUseCaseImpl
import gamescreen.menu.usecase.getviewmodelbycommandtype.GetControllerByCommandTypeUseCase
import gamescreen.menu.usecase.getviewmodelbycommandtype.GetControllerByCommandTypeUseCaseImpl
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

    single {
        ToolUserViewModel()
    }

    single {
        ToolListViewModel()
    }

    single {
        ToolTargetViewModel()
    }

    single<MenuStateRepository> {
        MenuStateRepositoryImpl()
    }

    single<UserRepository> {
        UserRepositoryImpl()
    }

    single<UseItemIdRepository> {
        UseItemIdRepositoryImpl()
    }

    single<TargetRepository> {
        TargetRepositoryImpl()
    }

    single<IndexRepository> {
        IndexRepositoryImpl()
    }

    single<BagRepository> {
        BagRepositoryImpl()
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
            toolTargetViewModel = get(),
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
            userRepository = get(),
            usedItemIdRepository = get(),
            skillRepository = get(),
            updateStatusService = get(),
        )
    }

    single<AddToolUseCase> {
        AddToolUseCaseImpl()
    }
}
