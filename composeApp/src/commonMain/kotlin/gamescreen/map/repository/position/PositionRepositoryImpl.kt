package gamescreen.map.repository.position

import core.domain.realm.Position
import core.domain.realm.PositionRealm
import core.domain.realm.convert
import data.INITIAL_MAP_X
import data.INITIAL_MAP_Y
import gamescreen.map.data.MapData
import gamescreen.map.data.toId
import gamescreen.map.domain.ObjectHeight
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query

class PositionRepositoryImpl : PositionRepository {
    private val config: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(PositionRealm::class))
    private val realm = Realm.open(config)

    private val position: PositionRealm
        get() {
            val data = realm.query<PositionRealm>().first().find()
            if (data != null) {
                return data
            }

            val position = PositionRealm().apply {
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

    override fun save(
        x: Int,
        y: Int,
        playerDx: Float,
        playerDy: Float,
        objectHeight: ObjectHeight,
        mapData: MapData,
    ) {
        realm.writeBlocking {
            findLatest(position)!!.apply {
                mapY = y
                mapX = x
                playerX = playerDx
                playerY = playerDy
                height = objectHeight.toInt()
                heightDetail = objectHeight.height
                mapId = mapData.toId()
            }
        }
    }

    override fun position(): Position {
        return position.convert()
    }
}
