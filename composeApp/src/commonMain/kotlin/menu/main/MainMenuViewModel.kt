package menu.main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainMenuViewModel {

    private val mutableSelectedFlow = MutableStateFlow<Int>(0)
    val selectedFlow: StateFlow<Int> = mutableSelectedFlow.asStateFlow()

    fun setSelected(selected: Int) {
        mutableSelectedFlow.value = selected
    }

    lateinit var pairedList: MutableList<Pair<MainMenuItem, MainMenuItem?>>

    fun setItems(items: List<MainMenuItem>) {
        pairedList = mutableListOf()
        for (cnt: Int in items.indices step 2) {
            if (cnt + 1 < items.size) {
                //　次の項目とセットで追加
                pairedList.add(Pair(items[cnt], items[cnt + 1]))
            } else {
                // もう項目がないのでnullを入れる
                pairedList.add(Pair(items[cnt], null))
            }
        }
    }
}
