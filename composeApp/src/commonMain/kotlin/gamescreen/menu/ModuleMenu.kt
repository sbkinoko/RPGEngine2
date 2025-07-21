package gamescreen.menu

import core.EquipmentBagRepositoryName
import core.PlayerStatusRepositoryName
import core.ToolBagRepositoryName
import core.UpdatePlayer
import core.UseSkillUseCaseName
import core.UseToolUseCaseName_
import core.usecase.item.useitem.UseItemUseCase
import core.usecase.item.useitem.UseSkillUseCaseImpl
import data.item.equipment.EquipmentId
import data.item.tool.ToolId
import gamescreen.menu.component.StatusComponentViewModel
import gamescreen.menu.item.equipment.list.EquipmentListViewModel
import gamescreen.menu.item.equipment.target.EquipmentTargetViewModel
import gamescreen.menu.item.equipment.user.EquipmentUserViewModel
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
import gamescreen.menu.repository.menustate.MenuStateRepository
import gamescreen.menu.repository.menustate.MenuStateRepositoryImpl
import gamescreen.menu.status.StatusViewModel
import gamescreen.menu.usecase.backfield.CloseMenuUseCase
import gamescreen.menu.usecase.backfield.CloseMenuUseCaseImpl
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.menu.usecase.bag.addtool.AddToolUseCaseImpl
import gamescreen.menu.usecase.bag.dectool.DecItemUseCase
import gamescreen.menu.usecase.bag.dectool.DecToolUseCaseImpl
import gamescreen.menu.usecase.getviewmodelbycommandtype.GetControllerByCommandTypeUseCase
import gamescreen.menu.usecase.getviewmodelbycommandtype.GetControllerByCommandTypeUseCaseImpl
import gamescreen.menu.usecase.givetool.GiveToolUseCase
import gamescreen.menu.usecase.givetool.GiveToolUseCaseImpl
import gamescreen.menu.usecase.usetoolinmap.UseItemInMapUseCase
import gamescreen.menu.usecase.usetoolinmap.UseItemInMapUseCaseImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val DecToolUseCaseName = named(
    "DecToolUseCase"
)
val DecEquipmentUseCaseName = named(
    "DecEquipmentUseCase"
)

val qualifierAddToolUseCase = named(
    "AddToolUseCase"
)

val qualifierAddEquipmentUseCase = named(
    "AddEquipmentUseCase"
)

val ModuleMenu = module {
    single {
        MenuViewModel()
    }

    single {
        MainMenuViewModel()
    }

    single {
        StatusViewModel(
            statusDataRepository = get(
                PlayerStatusRepositoryName,
            )
        )
    }

    single {
        StatusComponentViewModel(
            statusDataRepository = get(
                PlayerStatusRepositoryName,
            )
        )
    }

    single {
        SkillUserViewModel(
            statusDataRepository = get(
                PlayerStatusRepositoryName,
            )
        )
    }

    single {
        SkillListViewModel(
            useItemInMapUseCase = get(
                qualifier = UseSkillUseCaseName,
            )
        )
    }

    single {
        SkillTargetViewModel(
            useItemInMapUseCase = get(
                qualifier = UseSkillUseCaseName,
            ),
        )
    }

    single {
        ToolUserViewModel(
            statusDataRepository = get(
                PlayerStatusRepositoryName,
            )
        )
    }

    single {
        ToolListViewModel(
            useItemInMapUseCase = get(
                qualifier = UseToolUseCaseName_,
            ),
        )
    }

    single {
        ToolTargetViewModel(
            useItemInMapUseCase = get(
                qualifier = UseToolUseCaseName_
            ),
        )
    }

    single {
        ToolGiveUserViewModel(
            statusDataRepository = get(
                PlayerStatusRepositoryName,
            )
        )
    }

    single {
        EquipmentUserViewModel(
            statusDataRepository = get(
                qualifier = PlayerStatusRepositoryName,
            ),
        )
    }

    single {
        EquipmentListViewModel(
            // fixme injectしないクラス設計を考える
            //　未使用クラス
            useItemInMapUseCase = get(
                qualifier = UseToolUseCaseName_
            ),
        )
    }

    single {
        EquipmentTargetViewModel(
            equipmentUseCase = get(),
            decEquipmentUseCase = get(
                qualifier = DecEquipmentUseCaseName,
            ),
            addEquipmentUseCase = get(
                qualifier = qualifierAddEquipmentUseCase,
            )
        )
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

            equipmentUserViewModel = get(),
            equipmentListViewModel = get(),
            equipmentTargetViewModel = get(),
        )
    }

    single<CloseMenuUseCase> {
        CloseMenuUseCaseImpl(
            menuStateRepository = get(),
            changeToMapUseCase = get(),
        )
    }

    single<UseItemUseCase> {
        UseSkillUseCaseImpl(
            playerStatusRepository = get(),
            skillRepository = get(),
            updateStatus = get(
                qualifier = named(UpdatePlayer)
            ),
        )
    }

    single<AddToolUseCase<ToolId>>(
        qualifier = qualifierAddToolUseCase,
    ) {
        AddToolUseCaseImpl(
            bagRepository = get(
                qualifier = ToolBagRepositoryName,
            ),
        )
    }

    single<DecItemUseCase<ToolId>>(
        qualifier = DecToolUseCaseName,
    ) {
        DecToolUseCaseImpl(
            bagRepository = get(
                qualifier = ToolBagRepositoryName
            ),
        )
    }

    single<AddToolUseCase<EquipmentId>>(
        qualifier = qualifierAddEquipmentUseCase
    ) {
        AddToolUseCaseImpl(
            bagRepository = get(
                qualifier = EquipmentBagRepositoryName,
            )
        )
    }

    single<DecItemUseCase<EquipmentId>>(
        qualifier = DecEquipmentUseCaseName,
    ) {
        DecToolUseCaseImpl(
            bagRepository = get(
                qualifier = EquipmentBagRepositoryName,
            )
        )
    }

    single<GiveToolUseCase> {
        GiveToolUseCaseImpl(
            targetRepository = get(),
            userRepository = get(),
            indexRepository = get(),
            bagRepository = get(
                qualifier = ToolBagRepositoryName,
            ),
            playerStatusRepository = get(),
            decToolUseCase = get(
                qualifier = DecToolUseCaseName,
            ),
            addToolUseCase = get(
                qualifier = qualifierAddToolUseCase,
            ),
        )
    }

    single<UseItemInMapUseCase>(
        qualifier = UseToolUseCaseName_
    ) {
        UseItemInMapUseCaseImpl(
            menuStateRepository = get(),
            textRepository = get(),
            indexRepository = get(),
            userRepository = get(),
            targetRepository = get(),
            useItemUseCase = get(
                qualifier = UseToolUseCaseName_
            ),
        )
    }

    single<UseItemInMapUseCase>(
        qualifier = UseSkillUseCaseName
    ) {
        UseItemInMapUseCaseImpl(
            menuStateRepository = get(),
            textRepository = get(),
            indexRepository = get(),
            userRepository = get(),
            targetRepository = get(),
            useItemUseCase = get(
                qualifier = UseSkillUseCaseName
            ),
        )
    }
}
