package core.confim

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import core.confim.repository.ConfirmRepository
import core.domain.Choice
import core.menu.SelectableChildViewModel
import gamescreen.menu.domain.SelectManager
import org.koin.core.component.inject

class ConfirmViewModel : SelectableChildViewModel<Boolean>() {
    override val commandRepository: ConfirmRepository by inject()

    @Composable
    fun getShowStateAsState(): State<Boolean> {
        return commandRepository.commandTypeFlow.collectAsState(
            false
        )
    }

    // fixme 選択肢の数を増やせるようにする
    // fixme 選択肢の順番を入れ替えたいのでBボタン/window外タップの処理をなくす
    override val canBack: Boolean
        get() = true

    var choice: List<Choice> = listOf()

    override fun goNextImpl() {
        choice[selectManager.selected].callBack()
    }


    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 2,
    )

    override fun isBoundedImpl(commandType: Boolean): Boolean {
        return commandType
    }
}
