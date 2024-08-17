package battle.command.main

import battle.domain.PlayerActionCommand
import battle.repository.commandstate.CommandStateRepository
import common.Timer
import common.menu.CommonMenuViewModel
import menu.domain.SelectManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : CommonMenuViewModel(), KoinComponent {
    private val commandStateRepository: CommandStateRepository by inject()

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )

    fun onClickItem(
        id: Int,
        callback: () -> Unit,
    ) {
        // 選択されていたらコールバック
        if (selectManager.selected == id) {
            callback()
            return
        }

        //　今クリックしたやつを選択
        selectManager.selected = id
    }

    fun goNext() {
        when (selectManager.selected) {
            0 -> commandStateRepository.push(
                PlayerActionCommand(
                    playerId = 0,
                )
            )
        }
    }

    override var timer: Timer = Timer(awaitTime = 200L)
    override var pressA: () -> Unit = {

    }
}
