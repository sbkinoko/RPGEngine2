package org.example.project

import android.app.Application
import battle.BattleModule
import common.CommonModule
import main.MainModule
import map.MapModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RPGApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RPGApplication)
            modules(
                MapModule,
                BattleModule,
                CommonModule,
                MainModule,
            )
        }
    }
}
