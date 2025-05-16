package gamescreen.map.usecase.move

import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.square.NormalRectangle
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

    private val moveNPCUseCase: MoveNPCUseCase,

    private val velocityManageService: VelocityManageService,
    private val makeFrontDateService: MakeFrontDateService,
) : MoveUseCase {

    override suspend fun invoke(
        actualVelocity: Velocity,
        tentativeVelocity: Velocity,
        mapUiState: MapUiState,
        fieldSquare: NormalRectangle,
        playerMoveArea: NormalRectangle,
    ): MapUiState {
        val mediatedVelocity =
            velocityManageService.invoke(
                tentativePlayerVelocity = actualVelocity,
                playerMoveArea = playerMoveArea,
                playerSquare = mapUiState.player.square,
            )

        var movedPlayer = mapUiState.player.copy(
            actualVelocity = mediatedVelocity.first,
            tentativeVelocity = tentativeVelocity,
        ).move()

        val movedBackgroundData = moveBackgroundUseCase.invoke(
            velocity = mediatedVelocity.second,
            fieldSquare = fieldSquare,
            backgroundData = mapUiState.backgroundData,
        )

        val movedNPCData = moveNPCUseCase.invoke(
            velocity = mediatedVelocity.second,
            npcData = mapUiState.npcData,
        )

        movedPlayer = movedPlayer.copy(
            eventType = getEventTypeUseCase.invoke(
                rectangle = movedPlayer.eventSquare,
                backgroundData = movedBackgroundData,
                npcData = mapUiState.npcData,
            )
        )

        // playerが入っているマスを設定
        val cell = updateCellContainPlayerUseCase.invoke(
            player = movedPlayer,
            backgroundData = movedBackgroundData,
        )

        val frontObjectData = makeFrontDateService.invoke(
            backgroundData = movedBackgroundData,
            player = movedPlayer
        )

        return MapUiState(
            player = movedPlayer,
            backgroundData = movedBackgroundData,
            frontObjectData = frontObjectData.first,
            backObjectData = frontObjectData.second,
            npcData = movedNPCData,
            playerIncludeCell = cell,
        )
    }
}
