package menu

import menu.main.MainMenuViewModel
import menu.repository.menustate.MenuStateRepository
import menu.repository.menustate.MenuStateRepositoryImpl
import menu.skill.list.SkillListViewModel
import menu.skill.repository.skilluser.SkillUserRepository
import menu.skill.repository.skilluser.SkillUserRepositoryImpl
import menu.skill.repository.target.TargetRepository
import menu.skill.repository.target.TargetRepositoryImpl
import menu.skill.repository.useid.UseSkillIdRepository
import menu.skill.repository.useid.UseSkillIdRepositoryImpl
import menu.skill.target.SkillTargetViewModel
import menu.skill.usecase.getskillexplain.GetSkillExplainUseCase
import menu.skill.usecase.getskillexplain.GetSkillExplainUseCaseImpl
import menu.skill.usecase.useskill.UseSkillUseCase
import menu.skill.usecase.useskill.UseSkillUseCaseImpl
import menu.skill.user.SkillUserViewModel
import menu.status.StatusViewModel
import menu.usecase.backfield.BackFieldUseCase
import menu.usecase.backfield.BackFieldUseCaseImpl
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
