package gamescreen.map.usecase.battlenormal

import core.domain.BattleEventCallback
import core.domain.mapcell.CellType
import core.domain.mapcell.toBattleBackGround
import core.usecase.restart.RestartUseCase
import gamescreen.map.domain.MapUiState
import gamescreen.map.repository.playercell.PlayerCellRepository
import gamescreen.map.usecase.battledecidemonster.DecideBattleMonsterUseCase
import gamescreen.map.usecase.battlestart.StartBattleUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartNormalBattleUseCaseImpl(
    private val playerCellRepository: PlayerCellRepository,
    private val decideBattleMonsterUseCase: DecideBattleMonsterUseCase,
    private val startBattleUseCase: StartBattleUseCase,

    private val textRepository: TextRepository,
    private val restartUseCase: RestartUseCase,
) : StartNormalBattleUseCase {
    override fun invoke(
        mapUiState: MapUiState,
        updateScreen: (MapUiState) -> Unit,
    ) {
        val backgroundCell = playerCellRepository.playerCenterCell

        val cellType = backgroundCell.cellType as? CellType.MonsterCell
            ?: return

        val monsterList = decideBattleMonsterUseCase.invoke(
            backgroundCell = backgroundCell,
        )

        val backgroundType = cellType.toBattleBackGround()

        startBattleUseCase.invoke(
            monsterList = monsterList,
            backgroundType = backgroundType,
            battleEventCallback = BattleEventCallback(
                winCallback = {},
                loseCallback = {
                    CoroutineScope(Dispatchers.Default).launch {
                        updateScreen(
                            restartUseCase.invoke(
                                mapUiState = mapUiState,
                            ),
                        )
                        textRepository.push(
                            textBoxData = TextBoxData(
                                text = "全滅してしまった",
                            )
                        )
                    }
                },
            )
        )
    }
}
