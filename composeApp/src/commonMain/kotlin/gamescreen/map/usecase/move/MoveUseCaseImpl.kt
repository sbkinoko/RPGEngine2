package gamescreen.map.usecase.move

import gamescreen.map.domain.Player
import gamescreen.map.domain.UIData
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.NormalRectangle
import gamescreen.map.repository.npc.NPCRepository
import gamescreen.map.service.makefrontdata.MakeFrontDateService
import gamescreen.map.service.velocitymanage.VelocityManageService
import gamescreen.map.usecase.collision.geteventtype.GetEventTypeUseCase
import gamescreen.map.usecase.movebackground.MoveBackgroundUseCase
import gamescreen.map.usecase.movenpc.MoveNPCUseCase
import gamescreen.map.usecase.updatecellcontainplayer.UpdateCellContainPlayerUseCase

// todo テスト作成
class MoveUseCaseImpl(
    private val getEventTypeUseCase: GetEventTypeUseCase,
    private val updateCellContainPlayerUseCase: UpdateCellContainPlayerUseCase,

    private val moveBackgroundUseCase: MoveBackgroundUseCase,

    private val npcRepository: NPCRepository,
    private val moveNPCUseCase: MoveNPCUseCase,

    private val velocityManageService: VelocityManageService,
    private val makeFrontDateService: MakeFrontDateService,
) : MoveUseCase {

    override suspend fun invoke(
        actualVelocity: Velocity,
        tentativeVelocity: Velocity,
        backgroundData: BackgroundData,
        player: Player,
        fieldSquare: NormalRectangle,
        playerMoveArea: NormalRectangle,
    ): UIData {
        val mediatedVelocity =
            velocityManageService.invoke(
                tentativePlayerVelocity = actualVelocity,
                playerMoveArea = playerMoveArea,
                playerSquare = player.square,
            )

        var movedPlayer = player.copy(
            actualVelocity = mediatedVelocity.first,
            tentativeVelocity = tentativeVelocity,
        ).move()

        val movedBackgroundData = moveBackgroundUseCase.invoke(
            velocity = mediatedVelocity.second,
            fieldSquare = fieldSquare,
            backgroundData = backgroundData,
        )

        val npcData = npcRepository.npcStateFlow.value
        val movedData = moveNPCUseCase.invoke(
            velocity = mediatedVelocity.second,
            npcData = npcData,
        )

        movedPlayer = movedPlayer.copy(
            eventType = getEventTypeUseCase.invoke(
                movedPlayer.eventSquare,
                backgroundData = movedBackgroundData,
            )
        )

        // playerが入っているマスを設定
        updateCellContainPlayerUseCase.invoke(
            player = movedPlayer,
            backgroundData = movedBackgroundData,
        )

        npcRepository.setNpc(
            npcData = movedData,
        )

        val frontObjectData = makeFrontDateService.invoke(
            backgroundData = movedBackgroundData,
            player = movedPlayer
        )

        return UIData(
            player = movedPlayer,
            backgroundData = movedBackgroundData,
            frontObjectData = frontObjectData.first,
            backObjectData = frontObjectData.second,
            npcData = npcData,
        )
    }
}
