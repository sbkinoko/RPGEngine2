package org.example.project

import android.app.Application
import core.ModuleCore
import gamescreen.battle.ModuleBattle
import gamescreen.choice.ModuleChoice
import gamescreen.map.ModuleMap
import gamescreen.menu.ModuleMenu
import gamescreen.text.ModuleText
import main.ModuleMain
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
                ModuleText,
                ModuleChoice,

                ModuleCore,
            )
        }
    }
}
