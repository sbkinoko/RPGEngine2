package gamescreen.map.viewmodel

import common.DefaultScope
import controller.domain.ControllerCallback
import controller.domain.Stick
import core.domain.Position
import core.domain.mapcell.CellType
import core.repository.screentype.ScreenTypeRepository
import data.INITIAL_MAP_DATA
import data.INITIAL_MAP_X
import data.INITIAL_MAP_Y
import gamescreen.GameScreenType
import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.Player
import gamescreen.map.domain.Point
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.background.ObjectData
import gamescreen.map.domain.npc.NPCData
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.encouter.EncounterRepository
import gamescreen.map.repository.npc.NPCRepository
import gamescreen.map.repository.player.PlayerPositionRepository
import gamescreen.map.repository.playercell.PlayerCellRepository
import gamescreen.map.usecase.PlayerMoveManageUseCase
import gamescreen.map.usecase.battlenormal.StartNormalBattleUseCase
import gamescreen.map.usecase.collision.iscollidedevent.IsCollidedEventUseCase
import gamescreen.map.usecase.event.actionevent.ActionEventUseCase
import gamescreen.map.usecase.event.cellevent.CellEventUseCase
import gamescreen.map.usecase.move.MoveUseCase
import gamescreen.map.usecase.roadmap.RoadMapUseCase
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.event.EventType

class MapViewModel(
    private val encounterRepository: EncounterRepository,
    private val startNormalBattleUseCase: StartNormalBattleUseCase,

    private val moveUseCase: MoveUseCase,
) : ControllerCallback, KoinComponent {
    private val playerPositionRepository: PlayerPositionRepository by inject()
    private val playerMoveManageUseCase: PlayerMoveManageUseCase by inject()

    private val screenTypeRepository: ScreenTypeRepository by inject()

    private val backgroundRepository: BackgroundRepository by inject()
    private val playerCellRepository: PlayerCellRepository by inject()

    private val npcRepository: NPCRepository by inject()

    private val isEventCollidedEventUseCase: IsCollidedEventUseCase by inject()

    private val actionEventUseCase: ActionEventUseCase by inject()
    private val cellEventUseCase: CellEventUseCase by inject()

    private val roadMapUseCase: RoadMapUseCase by inject()

    val playerSquare: StateFlow<Player> =
        playerPositionRepository.playerPositionStateFlow

    private var tapPoint: Point? = null

    private var tentativePlayerVelocity: Velocity = Velocity()

    private val canEvent: Boolean
        get() = playerPositionRepository.playerPositionStateFlow.value.eventType.canEvent

    private val mutableUiStateFlow = MutableStateFlow(
        MapUiState(
            player = playerPositionRepository.playerPositionStateFlow.value,
            npcData = NPCData(emptyList()),
            backgroundData = BackgroundData(emptyList()),
            frontObjectData = ObjectData(emptyList()),
            backObjectData = ObjectData(emptyList()),
            playerIncludeCell = null,
        )
    )

    val uiStateFlow = mutableUiStateFlow.asStateFlow()

    private val config: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(Position::class))
    private val realm = Realm.open(config)
    val position: Position
        get() {
            val data = realm.query<Position>().first().find()
            if (data != null) {
                return data
            }

            val position = Position().apply {
                mapX = INITIAL_MAP_X
                mapY = INITIAL_MAP_Y
            }

            realm.writeBlocking {
                copyToRealm(
                    position
                )
            }

            return position
        }

    init {
        backgroundRepository.cellNum = CELL_NUM
        backgroundRepository.screenSize = VIRTUAL_SCREEN_SIZE

        CoroutineScope(Dispatchers.Default).launch {
            playerPositionRepository.setPlayerPosition(
                player = Player(
                    size = VIRTUAL_PLAYER_SIZE,
                )
            )

            val data = position

            roadMapUseCase.invoke(
                mapX = data.mapX,
                mapY = data.mapY,
                mapData = INITIAL_MAP_DATA,
            ).apply {
                mutableUiStateFlow.value = uiStateFlow.value
                    .copy(
                        player = player!!,
                        backgroundData = backgroundData!!,
                        frontObjectData = frontObjectData!!,
                        backObjectData = backObjectData!!,
                    )
            }

            delay(50)
            mutableUiStateFlow.value = uiStateFlow.value.copy(
                npcData = npcRepository.npcStateFlow.value,
            )
        }

        DefaultScope.launch {
            playerCellRepository.playerIncludeCellFlow.collect {
                mutableUiStateFlow.value = uiStateFlow.value.copy(
                    playerIncludeCell = it,
                )
            }
        }
    }

    private var autoEvent: EventType? = null

    /**
     * 主人公の位置を更新
     */
    suspend fun updatePosition() {
        // fixme テストに影響がありそうなので確認する

        // タップしていたら位置を更新
        if (tapPoint != null) {
            updateVelocityByTap(tapPoint!!)
        }

        //速度が0なら何もしない
        if (tentativePlayerVelocity.isMoving.not()) {
            return
        }

        val player = playerSquare.value

        val actualVelocity = playerMoveManageUseCase
            .getMovableVelocity(
                player = player,
                tentativePlayerVelocity = tentativePlayerVelocity,
            )

        // 表示物を移動
        val uiData = moveUseCase.invoke(
            actualVelocity = actualVelocity,
            tentativeVelocity = tentativePlayerVelocity,
        )

        mutableUiStateFlow.value = uiStateFlow.value.copy(
            player = uiData.player!!,
            npcData = uiData.npcData!!,
            backgroundData = uiData.backgroundData!!,
            backObjectData = uiData.backObjectData!!,
            frontObjectData = uiData.frontObjectData!!,
        )

        realm.writeBlocking {
            findLatest(position)!!.apply {
                mapY = playerCellRepository.playerCenterCell.mapPoint.y

                mapX = playerCellRepository.playerCenterCell.mapPoint.x
            }
        }

        val preEvent = autoEvent
        autoEvent = isEventCollidedEventUseCase.invoke(
            playerSquare = player.square
        )

        if (autoEvent != preEvent && autoEvent != null) {
            actionEventUseCase.invoke(autoEvent!!)
        }

        // fixme moveUseCaseに移動する
        // 水上から陸に上がったとき、そこが移動マスならば、移動を呼び出したい
        //　プレイヤーが今いるマスに基づいてイベントを呼び出し
        playerCellRepository.eventCell?.let { cell ->
            cellEventUseCase.invoke(
                cell.cellType,
            )
            // 戦闘せずに終了
            return
        }

        val playerCenterCell = playerCellRepository.playerCenterCell

        val monsterCell = playerCenterCell.cellType as? CellType.MonsterCell
            ?: return
        val encounterDistance = actualVelocity.scalar * monsterCell.distanceLate
        if (encounterRepository.judgeStartBattle(
                distance = encounterDistance
            )
        ) {
            startNormalBattleUseCase.invoke()
            resetTapPoint()
        }
    }

    /**
     * @param x tapのx座標
     * @param y tapのy座標
     */
    fun setTapPoint(
        x: Float,
        y: Float,
    ) {
        tapPoint = Point(
            x = x,
            y = y,
        )
    }

    /**
     * タップの位置に対して速度を計算
     */
    private fun updateVelocityByTap(tapPoint: Point) {
        val square = playerPositionRepository.getPlayerPosition().square
        val dx = (tapPoint.x) - (square.x + playerSquare.value.size / 2)
        val dy = (tapPoint.y) - (square.y + playerSquare.value.size / 2)
        tentativePlayerVelocity = Velocity(
            x = dx,
            y = dy,
        )
    }

    private fun updateVelocityByStick(dx: Float, dy: Float) {
        // fixme repositoryから取ってないのでデータが反映されてない
        // maxVが0なので初回のスティックだと動かない
        val vx = playerSquare.value.maxVelocity * dx
        val vy = playerSquare.value.maxVelocity * dy
        tentativePlayerVelocity = Velocity(
            x = vx,
            y = vy,
        )
    }

    /**
     * タップしてない状態にする
     */
    fun resetTapPoint() {
        val velocity = Velocity(
            x = 0f,
            y = 0f,
        )
        tapPoint = null

        tentativePlayerVelocity = velocity
    }

    fun getAroundCellId(x: Int, y: Int): List<List<CellType>> {
        return backgroundRepository.getBackgroundAround(
            x = x,
            y = y,
        )
    }

    private fun event() {
        actionEventUseCase.invoke(
            eventType = playerSquare.value.eventType,
        )
    }

    fun touchCharacter() {
        if (canEvent) {
            event()
        } else {
            showMenu()
        }
    }

    fun touchEventSquare() {
        if (canEvent) {
            event()
        }
    }

    override fun pressA() {
        if (canEvent) {
            event()
        }
    }

    override fun pressB() {
        startNormalBattleUseCase.invoke()
    }

    override fun pressM() {
        showMenu()
    }

    private fun showMenu() {
        screenTypeRepository.setScreenType(
            gameScreenType = GameScreenType.MENU
        )
    }

    override fun moveStick(
        stick: Stick,
    ) {
        updateVelocityByStick(
            dx = stick.x.toFloat(),
            dy = stick.y.toFloat(),
        )
    }

    companion object {
        const val MOVE_BORDER = 0.3f
        const val VIRTUAL_SCREEN_SIZE = 210
        const val VIRTUAL_PLAYER_SIZE = 20f
        const val CELL_NUM = 5

        const val CELL_SIZE = VIRTUAL_SCREEN_SIZE / CELL_NUM.toFloat()
    }
}
