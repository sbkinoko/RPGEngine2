package gamescreen.menushop

import gamescreen.menushop.repoisitory.ShopMenuRepository
import gamescreen.menushop.repoisitory.ShopMenuRepositoryImpl
import org.koin.dsl.module

val ModuleShop = module {

    single {
        ShopViewModel()
    }

    single<ShopMenuRepository> {
        ShopMenuRepositoryImpl()
    }
}
