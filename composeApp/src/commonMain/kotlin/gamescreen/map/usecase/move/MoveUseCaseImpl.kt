package gamescreen.map.usecase.move

import gamescreen.map.domain.Player
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.NormalRectangle
import gamescreen.map.domain.npc.NPCData
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.npc.NPCRepository
import gamescreen.map.repository.player.PlayerPositionRepository
import gamescreen.map.service.velocitymanage.VelocityManageService
import gamescreen.map.usecase.UpdateCellContainPlayerUseCase
import gamescreen.map.usecase.collision.geteventtype.GetEventTypeUseCase
import gamescreen.map.usecase.movebackground.MoveBackgroundUseCase
import gamescreen.map.usecase.movenpc.MoveNPCUseCase

// todo テスト作成
class MoveUseCaseImpl(
    private val playerPositionRepository: PlayerPositionRepository,
    private val getEventTypeUseCase: GetEventTypeUseCase,
    private val updateCellContainPlayerUseCase: UpdateCellContainPlayerUseCase,

    private val backgroundRepository: BackgroundRepository,
    private val moveBackgroundUseCase: MoveBackgroundUseCase,

    private val npcRepository: NPCRepository,
    private val moveNPCUseCase: MoveNPCUseCase,

    private val velocityManageService: VelocityManageService,
) : MoveUseCase {

    override suspend fun invoke(
        actualVelocity: Velocity,
        tentativeVelocity: Velocity,
        fieldSquare: NormalRectangle,
        playerMoveArea: NormalRectangle,
    ): UIData {
        var player = playerPositionRepository.playerPositionStateFlow.value

        val mediatedVelocity =
            velocityManageService.invoke(
                tentativePlayerVelocity = actualVelocity,
                playerMoveArea = playerMoveArea,
                playerSquare = player.square,
            )

        val backgroundData = backgroundRepository
            .backgroundStateFlow
            .value

        player = player.copy(
            actualVelocity = mediatedVelocity.first,
            tentativeVelocity = tentativeVelocity,
        ).move()

        val movedBackgroundData = moveBackgroundUseCase.invoke(
            velocity = mediatedVelocity.second,
            fieldSquare = fieldSquare,
            backgroundData = backgroundData
        )

        val npcData = npcRepository.npcStateFlow.value
        val movedData = moveNPCUseCase.invoke(
            velocity = mediatedVelocity.second,
            npcData = npcData,
        )

        player = player.copy(
            eventType = getEventTypeUseCase.invoke(
                player.eventSquare
            )
        )

        // playerが入っているマスを設定
        updateCellContainPlayerUseCase.invoke()

        playerPositionRepository.setPlayerPosition(
            player = player,
        )

        backgroundRepository.setBackground(
            background = movedBackgroundData,
        )
        npcRepository.setNpc(
            npcData = movedData,
        )
        return UIData(
            player,
            backgroundData,
            npcData,
        )
    }
}

data class UIData(
    val player: Player,
    val backgroundData: BackgroundData,
    val npcData: NPCData,
)
