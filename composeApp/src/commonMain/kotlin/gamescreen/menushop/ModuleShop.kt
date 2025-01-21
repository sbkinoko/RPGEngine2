package gamescreen.menushop

import gamescreen.menushop.domain.amountdata.AmountData
import gamescreen.menushop.domain.amountdata.AmountDataImpl
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.menushop.repository.shopmenu.ShopMenuRepositoryImpl
import org.koin.dsl.module

val ModuleShop = module {

    single {
        ShopViewModel(
            moneyRepository = get(),
            amountData = get(),
            choiceRepository = get(),
            textRepository = get(),
            addToolUseCase = get()
        )
    }

    single<ShopMenuRepository> {
        ShopMenuRepositoryImpl()
    }

    single<AmountData> {
        AmountDataImpl()
    }
}
