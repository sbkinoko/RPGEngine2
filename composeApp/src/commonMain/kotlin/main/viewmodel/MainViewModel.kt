package main.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import main.domain.ScreenType

class MainViewModel {
    private val mutableNowScreenType = MutableStateFlow(ScreenType.FIELD)
    val nowScreenType = mutableNowScreenType.asStateFlow()

    fun toBattle() {
        mutableNowScreenType.value = ScreenType.BATTLE
    }

    fun toField() {
        mutableNowScreenType.value = ScreenType.FIELD
    }
}
