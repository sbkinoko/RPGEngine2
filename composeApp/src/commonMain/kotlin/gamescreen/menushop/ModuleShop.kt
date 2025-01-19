package gamescreen.menushop

import gamescreen.menushop.amountdata.AmountData
import gamescreen.menushop.amountdata.AmountDataImpl
import gamescreen.menushop.repoisitory.shopmenu.ShopMenuRepository
import gamescreen.menushop.repoisitory.shopmenu.ShopMenuRepositoryImpl
import org.koin.dsl.module

val ModuleShop = module {

    single {
        ShopViewModel(
            moneyRepository = get(),
            amountData = get(),
        )
    }

    single<ShopMenuRepository> {
        ShopMenuRepositoryImpl()
    }

    single<AmountData> {
        AmountDataImpl()
    }
}
