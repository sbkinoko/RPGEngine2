package data.event.battle

import common.DefaultScope
import core.domain.BattleEventCallback
import core.usecase.heal.MaxHealUseCase
import data.event.EventManager
import data.monster.MonsterRepository
import gamescreen.battle.domain.BattleBackgroundType
import gamescreen.map.usecase.battlestart.StartBattleUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class EventManagerImpl(
    private val textRepository: TextRepository,
    private val maxHealUseCase: MaxHealUseCase,

    private val startBattleUseCase: StartBattleUseCase,
    private val monsterRepository: MonsterRepository,
) : EventManager<BattleEventKey>, KoinComponent {

    private val mutableEventFlag = MutableStateFlow(0)
    override val eventFlag = mutableEventFlag.asStateFlow()

    override fun callEvent(
        key: BattleEventKey,
    ) {
        val battleCallBack = BattleEventCallback(
            winCallback = {
                textRepository.push(
                    textBoxData = TextBoxData(
                        text = "おめでとう",
                        callBack = { mutableEventFlag.value = 1 },
                    )
                )
            },
            escapeCallback = {
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
            },
            loseCallback = {
                textRepository.push(
                    textBoxData = TextBoxData(
                        text = "逃げるのも時には大事だね",
                        callBack = { mutableEventFlag.value = 1 },
                    )
                )
            },
        )

        val talkData = listOf(
            TextBoxData(
                text = if (eventFlag.value == 0) {
                    "戦い"
                } else {
                    "再戦"
                },
                callBack = {
                    startBattleUseCase.invoke(
                        monsterList = List(5) {
                            monsterRepository.getMonster(0)
                        },
                        backgroundType = BattleBackgroundType.Event,
                        battleEventCallback = battleCallBack,

                        )
                },
            ),
        )
        textRepository.push(
            talkData,
        )
    }
}
