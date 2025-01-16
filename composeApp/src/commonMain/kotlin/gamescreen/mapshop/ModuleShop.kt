package gamescreen.mapshop

import gamescreen.mapshop.repoisitory.ShopMenuRepository
import gamescreen.mapshop.repoisitory.ShopMenuRepositoryImpl
import org.koin.dsl.module

val ModuleShop = module {

    single<ShopMenuRepository> {
        ShopMenuRepositoryImpl()
    }
}
