package gamescreen.map.usecase.resetnpc

import gamescreen.map.data.MapData
import gamescreen.map.repository.npc.NPCRepository
import gamescreen.map.viewmodel.MapViewModel
import kotlinx.coroutines.runBlocking

class ResetNPCPositionUseCaseImpl(
    private val npcRepository: NPCRepository,
) : ResetNPCPositionUseCase {

    override operator fun invoke(
        mapData: MapData,
        mapX: Int,
        mapY: Int,
    ) {
        runBlocking {
            // マップデータからNPCデータを読み込み
            val npcList = mapData.npcList.map {

                //中心データをもとに、表示位置を決定
                val x =
                    MapViewModel.VIRTUAL_SCREEN_SIZE / 2f +
                            (it.mapPoint.x - mapX) * MapViewModel.CELL_SIZE -
                            it.eventSquare.square.size / 2
                val y =
                    MapViewModel.VIRTUAL_SCREEN_SIZE / 2f +
                            (it.mapPoint.y - mapY) * MapViewModel.CELL_SIZE -
                            it.eventSquare.square.size / 2

                it.copy(
                    eventSquare = it.eventSquare.moveTo(
                        x = x,
                        y = y,
                    )
                )
            }

            npcRepository.setNpc(
                npcList,
            )
        }
    }
}
