package gamescreen.menu

import core.UpdatePlayer
import core.usecase.item.useskill.UseSkillUseCase
import core.usecase.item.useskill.UseSkillUseCaseImpl
import gamescreen.menu.component.StatusComponentViewModel
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.item.repository.index.IndexRepositoryImpl
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.item.repository.target.TargetRepositoryImpl
import gamescreen.menu.item.repository.user.UserRepository
import gamescreen.menu.item.repository.user.UserRepositoryImpl
import gamescreen.menu.item.skill.list.SkillListViewModel
import gamescreen.menu.item.skill.target.SkillTargetViewModel
import gamescreen.menu.item.skill.user.SkillUserViewModel
import gamescreen.menu.item.tool.give.ToolGiveUserViewModel
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
import gamescreen.menu.usecase.bag.dectool.DecToolUseCase
import gamescreen.menu.usecase.bag.dectool.DecToolUseCaseImpl
import gamescreen.menu.usecase.getviewmodelbycommandtype.GetControllerByCommandTypeUseCase
import gamescreen.menu.usecase.getviewmodelbycommandtype.GetControllerByCommandTypeUseCaseImpl
import gamescreen.menu.usecase.givetool.GiveToolUseCase
import gamescreen.menu.usecase.givetool.GiveToolUseCaseImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val ModuleMenu = module {
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
        StatusComponentViewModel()
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

    single {
        ToolGiveUserViewModel()
    }

    single<MenuStateRepository> {
        MenuStateRepositoryImpl()
    }

    single<UserRepository> {
        UserRepositoryImpl()
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
            menuStateRepository = get(),
            mainMenuViewModel = get(),
            statusViewModel = get(),
            skillUserViewModel = get(),
            skillListViewModel = get(),
            skillTargetViewModel = get(),
            toolUserViewModel = get(),
            toolListViewModel = get(),
            toolTargetViewModel = get(),
            toolGiveUserViewModel = get(),
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
            playerStatusRepository = get(),
            skillRepository = get(),
            updateStatus = get(
                qualifier = named(UpdatePlayer)
            ),
        )
    }

    single<AddToolUseCase> {
        AddToolUseCaseImpl(
            bagRepository = get(),
        )
    }

    single<DecToolUseCase> {
        DecToolUseCaseImpl(
            bagRepository = get(),
        )
    }

    single<GiveToolUseCase> {
        GiveToolUseCaseImpl(
            targetRepository = get(),
            userRepository = get(),
            indexRepository = get(),
            bagRepository = get(),
            playerStatusRepository = get(),
            decToolUseCase = get(),
            addToolUseCase = get(),
        )
    }
}
