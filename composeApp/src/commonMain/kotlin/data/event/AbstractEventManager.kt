package data.event

import core.domain.realm.RealmEvent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AbstractEventManager<T> : EventManager<T> {

    private val config: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmEvent::class))
    private val realm = Realm.open(config)

    protected val mutableStateFlow = MutableStateFlow(getData().flg)

    override val eventFlag: StateFlow<Int>
        get() = mutableStateFlow.asStateFlow()

    protected abstract val flgName: String

    val ini
        get() = RealmEvent().apply {
            name = flgName
            flg = 0
        }

    // 抽象クラスに入れる
    fun getData(): RealmEvent {
        return realm.query<RealmEvent>("name == '${flgName}'")
            .first()
            .find()
            ?: run {
                realm.writeBlocking {
                    copyToRealm(ini)
                }

                ini
            }
    }

    fun updateFlg(flg: Int) {
        mutableStateFlow.value = flg
        realm.writeBlocking {
            findLatest(getData())?.apply {
                this.flg = flg
            }
        }
    }
}
