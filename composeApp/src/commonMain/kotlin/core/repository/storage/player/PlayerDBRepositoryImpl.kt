package core.repository.storage.player

import core.domain.realm.RealmPlayer
import core.domain.realm.RealmPlayerList
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import org.mongodb.kbson.ObjectId

class PlayerDBRepositoryImpl : PlayerDBRepository {

    private val listConfig: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmPlayerList::class))
    private val listRealm = Realm.open(listConfig)

    private val statusConfig: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmPlayer::class))
    private val statusRealm = Realm.open(statusConfig)


    override fun setPlayers(players: List<Int>) {
        val idList = listRealm.query<RealmPlayerList>().first().find()


        for (i: Int in 0 until 3) {
            val id = idList!!.players[i]
            val exp = players[i]
            val player = statusRealm.query<RealmPlayer>(
                "id == $0", id,
            ).first().find()

            statusRealm.writeBlocking {
                findLatest(player!!)?.apply {
                    this.exp = exp
                }
            }
        }
    }

    override fun getPlayers(): List<Int> {
        var idList = listRealm.query<RealmPlayerList>().first().find()

        if (idList == null) {
            idList = RealmPlayerList()
            val list = realmListOf<ObjectId>()
            repeat(3) {
                list.add(ObjectId())
            }
            idList.players = list

            listRealm.writeBlocking {
                copyToRealm(idList)
            }
        }

        val expList = mutableListOf<Int>()

        for (id in idList.players) {
            var player = statusRealm.query<RealmPlayer>(
                "id == $0", id
            ).first().find()

            if (player == null) {
                player = RealmPlayer()
                player.id = id

                statusRealm.writeBlocking {
                    copyToRealm(
                        player
                    )
                }
            }

            expList.add(player.exp)
        }


        return expList
    }
}
