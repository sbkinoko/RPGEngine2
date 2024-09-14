package battle.service.attack

interface UpdateParameterService<T> {

    /**
     *  HPを減らして、更新後のステータスを返す
     */
    fun decHP(
        amount: Int,
        status: T,
    ): T

    /**
     *  HPを増やして、更新後のステータスを返す
     */
    fun incHP(
        amount: Int,
        status: T,
    ): T


    fun decMP(
        amount: Int,
        status: T,
    ): T

    fun incMP(
        amount: Int,
        status: T,
    )
}
