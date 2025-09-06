package core.repository.storage.tool

import core.domain.realm.RealmPlayerList
import core.domain.realm.RealmToolList
import core.domain.status.PlayerStatus
import data.repository.item.tool.ToolId
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import org.mongodb.kbson.ObjectId
import values.Constants

class ToolDBRepositoryImpl : ToolDBRepository {

    private val listConfig: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmPlayerList::class))
    private val listRealm = Realm.open(listConfig)

    private val toolConfig: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmToolList::class))
    private val toolRealm = Realm.open(toolConfig)


    override fun setPlayers(players: List<PlayerStatus>) {
        val idList = listRealm.query<RealmPlayerList>().first().find()

        for (i: Int in 0 until Constants.playerNum) {
            val id = idList!!.players[i]
            val tools = players[i].toolList
            val toolList = toolRealm.query<RealmToolList>(
                "id == $0", id,
            ).first().find()

            toolRealm.writeBlocking {
                findLatest(toolList!!)?.apply {
                    val list = realmListOf<Int>()
                    tools.map {
                        list.add(it.id)
                    }
                    this.tools = list
                }
            }
        }
    }

    override fun getTools(): List<List<ToolId>> {
        var idList = listRealm.query<RealmPlayerList>().first().find()

        if (idList == null) {
            idList = RealmPlayerList()
            val list = realmListOf<ObjectId>()
            repeat(Constants.playerNum) {
                list.add(ObjectId())
            }
            idList.players = list

            listRealm.writeBlocking {
                copyToRealm(idList)
            }
        }

        val toolListList = mutableListOf<List<ToolId>>()

        for (cnt: Int in 0 until idList.players.size) {
            val id = idList.players[cnt]

            var tools = toolRealm.query<RealmToolList>(
                "id == $0", id
            ).first().find()

            if (tools == null) {
                val list = when (cnt) {
                    0 -> listOf(
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL2,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL2,
                        ToolId.HEAL2,
                        ToolId.HEAL2,
                        ToolId.HEAL2,
                        ToolId.HEAL2,
                    )

                    1 -> listOf(
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                    )

                    else -> listOf(
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                        ToolId.HEAL1,
                    )
                }

                tools = RealmToolList()
                list.map {
                    tools.tools.add(it.id)
                }

                tools.id = id

                toolRealm.writeBlocking {
                    copyToRealm(
                        tools
                    )
                }
            }

            toolListList.add(tools.tools.map {
                ToolId.getEnum(it)
            })
        }

        return toolListList
    }
}
