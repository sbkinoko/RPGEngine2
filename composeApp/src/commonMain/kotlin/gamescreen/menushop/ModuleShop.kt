package gamescreen.menushop

import core.ToolBagRepositoryName
import gamescreen.menu.DecToolUseCaseName
import gamescreen.menu.qualifierAddToolUseCase
import gamescreen.menushop.domain.amountdata.AmountData
import gamescreen.menushop.domain.amountdata.AmountDataImpl
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.menushop.repository.shopmenu.ShopMenuRepositoryImpl
import gamescreen.menushop.usecase.setshopitem.SetShopItemUseCase
import gamescreen.menushop.usecase.setshopitem.SetShopItemUseCaseImpl
import gamescreen.menushop.viewmodel.BuyViewModel
import gamescreen.menushop.viewmodel.SellViewModel
import org.koin.dsl.module

val ModuleShop = module {

    single {
        BuyViewModel(
            moneyRepository = get(),
            amountData = get(),
            choiceRepository = get(),
            textRepository = get(),
            addToolUseCase = get(
                qualifier = qualifierAddToolUseCase,
            ),
            shopMenuRepository = get(),
        )
    }

    single {
        SellViewModel(
            moneyRepository = get(),
            amountData = get(),
            choiceRepository = get(),
            textRepository = get(),
            decToolUseCaseImpl = get(
                qualifier = DecToolUseCaseName,
            ),
            shopMenuRepository = get(),

            bagRepository = get(
                qualifier = ToolBagRepositoryName,
            ),
            toolRepository = get(),
        )
    }

    single<ShopMenuRepository> {
        ShopMenuRepositoryImpl()
    }

    single<SetShopItemUseCase> {
        SetShopItemUseCaseImpl(
            shopMenuRepository = get(),
        )
    }

    single<AmountData> {
        AmountDataImpl()
    }
}
