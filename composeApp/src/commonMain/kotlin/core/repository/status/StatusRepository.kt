package core.repository.status

// fixme statusDataに変更したい
// fixme 全データを返す処理を作成
interface StatusRepository<T> {
    /**
     * @param id id番目のstatusを取得
     */
    fun getStatus(id: Int): T

    /**
     * 全てのデータを返す
     */
    fun getStatusList(): List<T>

    /**
     * @param id id番目のstatusを更新
     */
    suspend fun setStatus(
        id: Int,
        status: T,
    )
}
