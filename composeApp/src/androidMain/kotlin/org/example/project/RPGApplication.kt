package org.example.project

import android.app.Application
import core.ModuleCore
import data.ModuleData
import data.event.ModuleEvent
import gamescreen.ModuleMain
import gamescreen.battle.ModuleBattle
import gamescreen.choice.ModuleChoice
import gamescreen.map.ModuleMap
import gamescreen.menu.ModuleMenu
import gamescreen.menushop.ModuleShop
import gamescreen.text.ModuleText
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RPGApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RPGApplication)

            modules(
                ModuleMain,

                ModuleMap,
                ModuleBattle,
                ModuleMenu,
                ModuleShop,

                ModuleText,
                ModuleChoice,

                ModuleCore,
                ModuleData,

                ModuleEvent,
            )
        }
    }
}
