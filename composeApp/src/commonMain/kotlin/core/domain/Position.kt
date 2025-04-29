package core.domain

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Position : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var mapX: Int = 0
    var mapY: Int = 0
}
