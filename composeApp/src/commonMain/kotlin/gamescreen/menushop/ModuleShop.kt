package gamescreen.menushop

import gamescreen.menu.qualifierAddToolUseCase
import gamescreen.menushop.domain.amountdata.AmountData
import gamescreen.menushop.domain.amountdata.AmountDataImpl
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.menushop.repository.shopmenu.ShopMenuRepositoryImpl
import gamescreen.menushop.usecase.setshopitem.SetShopItemUseCase
import gamescreen.menushop.usecase.setshopitem.SetShopItemUseCaseImpl
import org.koin.dsl.module

val ModuleShop = module {

    single {
        ShopViewModel(
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
