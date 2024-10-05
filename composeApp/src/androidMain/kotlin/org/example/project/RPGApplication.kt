package org.example.project

import android.app.Application
import core.CoreModule
import gamescreen.battle.BattleModule
import gamescreen.map.MapModule
import gamescreen.menu.MenuModule
import main.MainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RPGApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RPGApplication)
            modules(
                MainModule,

                MapModule,
                BattleModule,
                MenuModule,

                CoreModule,
            )
        }
    }
}
