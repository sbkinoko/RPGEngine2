package core.domain.realm

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class RealmPlayerList : RealmObject {
    @PrimaryKey
    var id: ObjectId = BsonObjectId()

    var players: RealmList<ObjectId> = realmListOf()
}
