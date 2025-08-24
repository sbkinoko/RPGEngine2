package core.domain.realm

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class MoneyRealm : RealmObject {
    @PrimaryKey
    var id: ObjectId = BsonObjectId()
    var amount: Int = -1
}
