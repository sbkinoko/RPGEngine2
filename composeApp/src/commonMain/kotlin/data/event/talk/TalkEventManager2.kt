package data.event.talk

import core.domain.realm.RealmEvent
import data.event.EventManager
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import values.event.EventGroup2

class TalkEventManager2(
    private val textRepository: TextRepository,
) : EventManager<EventGroup2> {

    // todo 抽象クラスを作ってそこに持たせる
    private val config: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmEvent::class))
    private val realm = Realm.open(config)

    private val mutableStateFlow = MutableStateFlow(getData().flg)

    override val eventFlag: StateFlow<Int>
        get() = mutableStateFlow.asStateFlow()

    // todo nameだけ実クラスで定義
    // iniは抽象クラスに入れる
    val ini = RealmEvent().apply {
        name = "test"
        flg = 0
    }

    // 抽象クラスに入れる
    fun getData(): RealmEvent {
        return realm.query<RealmEvent>("name == 'test'")
            .first()
            .find()
            ?: run {
                realm.writeBlocking {
                    copyToRealm(ini)
                }

                ini
            }
    }

    // todo 抽象クラスに入れる
    fun updateFlg(flg: Int) {
        mutableStateFlow.value = flg
        realm.writeBlocking {
            findLatest(getData())?.apply {
                this.flg = flg
            }
        }
    }

    override fun callEvent(key: EventGroup2) {
        var talkData: List<TextBoxData>

        when (key) {
            EventGroup2.Boy1 -> {
                talkData = listOf(
                    TextBoxData(
                        text = "ようこそ"
                    ),
                )
                updateFlg(1)
            }

            EventGroup2.Boy2 -> {
                talkData = if (mutableStateFlow.value == 0) {
                    listOf(
                        TextBoxData(
                            text = "先に左の人と話してね"
                        ),
                    )
                } else {
                    listOf(
                        TextBoxData(
                            text = "左の人と話したんだね"
                        ),
                    )
                }
            }
        }

        textRepository.push(
            talkData,
        )
    }
}
