package core.domain.realm

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
}

fun PositionRealm.convert(): Position = Position(
    mapX = this.mapX,
    mapY = this.mapY,
    playerX = this.playerX,
    playerY = this.playerY,
)
