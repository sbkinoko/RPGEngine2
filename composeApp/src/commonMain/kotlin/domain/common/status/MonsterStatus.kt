package domain.common.status

class MonsterStatus(
    val imgId: Int,
    name: String,
) : Status() {
    init {
        this.name = name
    }
}
