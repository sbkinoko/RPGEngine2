package core.usecase.updateparameter

interface UpdateStatusUseCase<T> {
    /**
     *  HPを減らして、更新後のステータスを返す
     */
    suspend fun decHP(
        id: Int,
        amount: Int,
    )

    /**
     *  HPを増やして、更新後のステータスを返す
     */
    suspend fun incHP(
        id: Int,
        amount: Int,
    )

    suspend fun decMP(
        id: Int,
        amount: Int,
    )

    suspend fun incMP(
        id: Int,
        amount: Int,
    )
}
