package core.domain.realm

import gamescreen.map.domain.ObjectHeight
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class PositionRealm : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var mapX: Int = 0
    var mapY: Int = 0
    var playerX: Float = 0f
    var playerY: Float = 0f
    var height: Int = 2
    var heightDetail: Int = 1
}

fun PositionRealm.convert(): Position = Position(
    mapX = this.mapX,
    mapY = this.mapY,
    playerX = this.playerX,
    playerY = this.playerY,
    objectHeight = ObjectHeight.ObjectHeight(
        height,
        heightDetail,
    )
)
