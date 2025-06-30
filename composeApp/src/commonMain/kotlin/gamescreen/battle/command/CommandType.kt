package gamescreen.battle.command

interface CommandType<T> {
    // 画面に表示するテキスト
    val menuString: String

    // enumのentries
    val entries: List<T>
}
