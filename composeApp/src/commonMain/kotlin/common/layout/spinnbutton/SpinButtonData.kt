package common.layout.spinnbutton

import kotlinx.coroutines.flow.StateFlow

interface SpinButtonData<T> {
    val dataFlow: StateFlow<T>

    fun onClickAdd()
    fun onClickDec()
}
