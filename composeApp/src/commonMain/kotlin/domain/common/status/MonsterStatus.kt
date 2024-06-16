package domain.common.status

// fixme IDだけ入れれば画像とモンスター名を引っ張って来れるようにする
class MonsterStatus(
    val imgId: Int,
    name: String,
) : Status() {
    init {
        this.name = name
    }
}
