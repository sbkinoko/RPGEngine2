package core.repository.memory.character

interface CharacterRepository<T> {
    /**
     * @param id id番目のstatusを更新
     */
    suspend fun setStatus(
        id: Int,
        status: T,
    )

    /**
     * @param id id番目のstatusを取得
     */
    fun getStatus(id: Int): T

    /**
     * 全てのデータをセット
     */
    suspend fun setStatusList(
        status: List<T>,
    )

    /**
     * 全てのデータを返す
     */
    fun getStatusList(): List<T>
}
