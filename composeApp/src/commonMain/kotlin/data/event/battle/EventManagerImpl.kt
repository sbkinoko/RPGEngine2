package data.event.battle

import common.DefaultScope
import core.usecase.heal.MaxHealUseCase
import data.event.EventManager
import gamescreen.battle.domain.BattleId
import gamescreen.map.usecase.battleevent.StartEventBattleUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventManagerImpl(
    private val textRepository: TextRepository,
    private val maxHealUseCase: MaxHealUseCase,
) : EventManager<BattleEventKey>, KoinComponent {

    private val mutableEventFlag = MutableStateFlow(0)
    override val eventFlag = mutableEventFlag.asStateFlow()

    // todo 循環参照になるので設計を考える
    private val startEventBattleUseCase: StartEventBattleUseCase by inject()

    override fun callEvent(
        key: BattleEventKey,
    ) {
        when (key) {
            BattleEventKey.Start -> {
                val talkData = listOf(
                    TextBoxData(
                        text = if (eventFlag.value == 0) {
                            "戦い"
                        } else {
                            "再戦"
                        },
                        callBack = {
                            startEventBattleUseCase.invoke(
                                battleId = BattleId.Battle1,
                            )
                        },
                    ),
                )
                textRepository.push(
                    talkData,
                )
            }

            BattleEventKey.Win -> {
                textRepository.push(
                    textBoxData = TextBoxData(
                        text = "おめでとう",
                        callBack = { mutableEventFlag.value = 1 },
                    )
                )
            }

            BattleEventKey.Lose -> {
                textRepository.push(
                    textBoxData = TextBoxData(
                        text = "また挑戦してね",
                        callBack = {
                            mutableEventFlag.value = 1
                            DefaultScope.launch {
                                maxHealUseCase.invoke()
                            }
                        }
                    ),
                )
            }

            BattleEventKey.Escape -> {
                textRepository.push(
                    textBoxData = TextBoxData(
                        text = "逃げるのも時には大事だね",
                        callBack = { mutableEventFlag.value = 1 },
                    )
                )
            }
        }
    }
}
