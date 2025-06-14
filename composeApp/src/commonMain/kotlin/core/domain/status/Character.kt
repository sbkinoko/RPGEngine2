package core.domain.status

// fixme ここに状態異常を入れると良さそう?
interface Character<T : StatusType> {
    val statusData: StatusData<T>
}
