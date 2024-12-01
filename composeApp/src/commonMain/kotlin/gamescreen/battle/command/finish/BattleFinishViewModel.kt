package gamescreen.battle.command.finish

import core.usecase.changetomap.ChangeToMapUseCase
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.FinishCommand
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.inject

class BattleFinishViewModel : BattleChildViewModel() {

    private val changeToMapUseCase: ChangeToMapUseCase by inject()

    private val mutableTextFLow = MutableStateFlow("")
    val textFlow = mutableTextFLow.asStateFlow()

    private var contentType: ContentType = ContentType.None

    fun init(
        isWin: Boolean,
    ) {
        if (isWin) {
            mutableTextFLow.value = "戦闘に勝利した"
            contentType = ContentType.Win
        } else {
            mutableTextFLow.value = "戦闘に敗北した"
            contentType = ContentType.Lose
        }
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is FinishCommand
    }

    // 使わない
    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )

    override fun goNextImpl() {
        when (contentType) {
            ContentType.Win -> {
                mutableTextFLow.value = "お金を手に入れた"
                contentType = ContentType.Money
            }

            ContentType.Money -> {
                mutableTextFLow.value = "経験値を手に入れた"
                contentType = ContentType.Exp
            }

            ContentType.Exp -> {
                finishBattle()
            }

            ContentType.Lose -> {
                finishBattle()
            }

            ContentType.None -> Unit
        }
    }

    private fun finishBattle() {
        changeToMapUseCase.invoke()
        mutableTextFLow.value = ""
        contentType = ContentType.None
    }
}
