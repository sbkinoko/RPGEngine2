package core.repository.storage

interface MoneyDBRepository {
    fun set(money: Int)

    fun get(): Int
}
