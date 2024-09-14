package common.repository.status

interface StatusRepository<T> {
    /**
     * @param id id番目のstatusを取得
     */
    fun getStatus(id: Int): T

    /**
     * @param id id番目のstatusを更新
     */
    suspend fun setStatus(
        id: Int,
        status: T,
    )
}
