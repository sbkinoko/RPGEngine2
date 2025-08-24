package core.repository.storage

import core.domain.realm.MoneyRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query

class MoneyDBRepositoryImpl : MoneyDBRepository {

    private val config: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(MoneyRealm::class))
    private val realm = Realm.open(config)

    private fun getData(): MoneyRealm {
        val data = realm.query<MoneyRealm>().first().find()

        if (data != null) {
            return data
        }

        val moneyData = MoneyRealm().apply {
            amount = 1000
        }

        realm.writeBlocking {
            copyToRealm(
                moneyData
            )
        }
        return moneyData
    }

    override fun set(money: Int) {
        realm.writeBlocking {
            findLatest(getData())!!.apply {
                amount = money
            }
        }
    }

    override fun get(): Int {
        return getData().amount
    }
}
