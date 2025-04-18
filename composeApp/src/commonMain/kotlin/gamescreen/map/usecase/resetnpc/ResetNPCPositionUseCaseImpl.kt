package gamescreen.map.usecase.resetnpc

import gamescreen.map.data.MapData
import gamescreen.map.domain.npc.NPCData
import gamescreen.map.repository.npc.NPCRepository
import gamescreen.map.viewmodel.MapViewModel
import kotlinx.coroutines.runBlocking

//todo test作る
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
                            it.eventRectangle.rectangle.width / 2
                val y =
                    MapViewModel.VIRTUAL_SCREEN_SIZE / 2f +
                            (it.mapPoint.y - mapY) * MapViewModel.CELL_SIZE -
                            it.eventRectangle.rectangle.height / 2

                it.copy(
                    eventRectangle = it.eventRectangle.moveTo(
                        x = x,
                        y = y,
                    )
                )
            }

            npcRepository.setNpc(
                NPCData(npcList),
            )
        }
    }
}
